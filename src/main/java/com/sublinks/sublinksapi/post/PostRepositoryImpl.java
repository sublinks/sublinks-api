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
    public List<Post> allPostsBySearchCriteria(final PostSearchCriteria postSearchCriteria) {

        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Post> cq = cb.createQuery(Post.class);

        final Root<Post> postTable = cq.from(Post.class);
        final Join<Post, Community> communityJoin = postTable.join("community", JoinType.INNER);

        final List<Predicate> predicates = new ArrayList<>();
        if ((postSearchCriteria.isSavedOnly() || !postSearchCriteria.isDislikedOnly()) && postSearchCriteria.person() != null) {
            if (postSearchCriteria.isSavedOnly() && !postSearchCriteria.isDislikedOnly()) {
                final Join<Post, PostSave> postPostSaveJoin = postTable.join("community", JoinType.INNER);
                predicates.add(cb.equal(postPostSaveJoin.get("person"), postSearchCriteria.person()));
            } else if (!postSearchCriteria.isSavedOnly() && postSearchCriteria.isDislikedOnly()) {
                final Join<Post, PostLike> postPostLikeJoin = postTable.join("post");
                predicates.add(cb.equal(postPostLikeJoin.get("person"), postSearchCriteria.person()));
            } else {
                // @todo throw error
            }
        }
        if (postSearchCriteria.communityIds() != null && !postSearchCriteria.communityIds().isEmpty()) {
            final Expression<Long> expression = communityJoin.get("id");
            predicates.add(expression.in(postSearchCriteria.communityIds()));
        }
        if (postSearchCriteria.person() != null && postSearchCriteria.listingType() == ListingType.Subscribed) {
            final Join<Community, LinkPersonCommunity> linkPersonCommunityJoin = communityJoin.join("linkPersonCommunity", JoinType.INNER);
            predicates.add(cb.equal(linkPersonCommunityJoin.get("person"), postSearchCriteria.person()));
        }
        if (postSearchCriteria.person() != null) {
            final Join<Post, PostLike> postPostLikeJoin = postTable.join("postLikes", JoinType.LEFT);
            predicates.add(cb.equal(postPostLikeJoin.get("person"), postSearchCriteria.person()));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        // @todo determine sort/pagination
        cq.orderBy(cb.desc(postTable.get("updatedAt")));

        return em.createQuery(cq).getResultList();
    }
}
