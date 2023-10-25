package com.sublinks.sublinksapi.post;

import com.sublinks.sublinksapi.community.Community;
import com.sublinks.sublinksapi.person.LinkPersonCommunity;
import com.sublinks.sublinksapi.person.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class PostRepositoryImpl implements PostRepositorySearch {

    @Autowired
    EntityManager em;

    @Override
    public List<Post> filterByFetchRequest(Person person, String communityName) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Post> cq = cb.createQuery(Post.class);

        // determine tables
        Root<Post> postTable = cq.from(Post.class);
        Join<Post, Community> communityJoin = postTable.join("community", JoinType.INNER);
        Join<Community, LinkPersonCommunity> linkPersonCommunityJoin = communityJoin.join("linkPersonCommunity", JoinType.INNER);

        // determine columns
        // post columns, only

        // determine where
        List<Predicate> predicates = new ArrayList<>();

        if (communityName != null) {
            predicates.add(cb.equal(communityJoin.get("titleSlug"), communityName));
        }

        predicates.add(cb.equal(linkPersonCommunityJoin.get("person"), person)); // subscribed by user 1

        cq.where(predicates.toArray(new Predicate[0]));
        cq.orderBy(cb.desc(postTable.get("updatedAt")));
        // determine sort/pagination
        List<Post> result = em.createQuery(cq).getResultList();
        return result;
    }
}
