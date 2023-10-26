package com.sublinks.sublinksapi.post;

import com.sublinks.sublinksapi.community.Community;
import com.sublinks.sublinksapi.person.LinkPersonCommunity;
import com.sublinks.sublinksapi.person.enums.ListingType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
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
    public List<Post> allPostsBySearchCriteria(SearchCriteria searchCriteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Post> cq = cb.createQuery(Post.class);

        Root<Post> postTable = cq.from(Post.class);
        Join<Post, Community> communityJoin = postTable.join("community", JoinType.INNER);

        List<Predicate> predicates = new ArrayList<>();
        if ((searchCriteria.isSavedOnly() || !searchCriteria.isDislikedOnly()) && searchCriteria.person() != null) {
            if (searchCriteria.isSavedOnly() && !searchCriteria.isDislikedOnly()) {
                Join<Post, PostSave> postPostSaveJoin = postTable.join("community", JoinType.INNER);
                predicates.add(cb.equal(postPostSaveJoin.get("person"), searchCriteria.person()));
            } else if (!searchCriteria.isSavedOnly() && searchCriteria.isDislikedOnly()) {
                Join<Post, PostLike> postPostLikeJoin = postTable.join("post");
                predicates.add(cb.equal(postPostLikeJoin.get("person"), searchCriteria.person()));
            } else {
                // throw error
            }
        }
        if (searchCriteria.communityIds() != null && !searchCriteria.communityIds().isEmpty()) {
            Expression<Long> expression = communityJoin.get("id");
            predicates.add(expression.in(searchCriteria.communityIds()));
        }
        if (searchCriteria.person() != null && searchCriteria.listingType() == ListingType.Subscribed) {
            Join<Community, LinkPersonCommunity> linkPersonCommunityJoin = communityJoin.join("linkPersonCommunity", JoinType.INNER);
            predicates.add(cb.equal(linkPersonCommunityJoin.get("person"), searchCriteria.person()));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        // @todo determine sort/pagination
        cq.orderBy(cb.desc(postTable.get("updatedAt")));

        return em.createQuery(cq).getResultList();
    }
}