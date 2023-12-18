package com.sublinks.sublinksapi.comment.repositories;

import com.sublinks.sublinksapi.comment.dto.Comment;
import com.sublinks.sublinksapi.comment.dto.CommentRead;
import com.sublinks.sublinksapi.comment.models.CommentSearchCriteria;
import com.sublinks.sublinksapi.community.dto.Community;
import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.person.dto.LinkPersonPost;
import com.sublinks.sublinksapi.person.dto.Person;
import com.sublinks.sublinksapi.person.enums.LinkPersonPostType;
import com.sublinks.sublinksapi.post.dto.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import static com.sublinks.sublinksapi.utils.PaginationUtils.applyPagination;

public class CommentRepositoryImpl implements CommentRepositorySearch {

  @Autowired
  EntityManager em;

  @Override
  public List<Comment> allCommentsBySearchCriteria(CommentSearchCriteria commentSearchCriteria) {

    final CriteriaBuilder cb = em.getCriteriaBuilder();
    final CriteriaQuery<Comment> cq = cb.createQuery(Comment.class);

    final Root<Comment> commentTable = cq.from(Comment.class);

    final List<Predicate> predicates = new ArrayList<>();
    // Post
    if (commentSearchCriteria.post() != null) {
      predicates.add(cb.equal(commentTable.get("post"), commentSearchCriteria.post()));
    }
    // Join for CommentView
    if (commentSearchCriteria.person() != null) {
      final Join<Comment, CommentRead> commentReadJoin = commentTable.join("commentReads",
          JoinType.LEFT);
      commentReadJoin.on(cb.equal(commentReadJoin.get("person"), commentSearchCriteria.person()));
    }

    if (commentSearchCriteria.community() != null) {
      predicates.add(cb.equal(commentTable.get("community"), commentSearchCriteria.community()));
    }

    cq.where(predicates.toArray(new Predicate[0]));

    // @todo determine hot / top / (controversial)

    switch (commentSearchCriteria.commentSortType()) {
      case New:
        cq.orderBy(cb.desc(commentTable.get("createdAt")));
        break;
      case Old:
        cq.orderBy(cb.asc(commentTable.get("createdAt")));
        break;
      default:
        cq.orderBy(cb.desc(commentTable.get("createdAt")));
        break;
    }

    final int perPage = Math.min(Math.abs(commentSearchCriteria.perPage()), 50);
    final int page = Math.max(commentSearchCriteria.page() - 1, 0);

    final TypedQuery<Comment> query = em.createQuery(cq);

    applyPagination(query, commentSearchCriteria.page(), perPage);

    return query.getResultList();
  }

  @Override
  public List<Comment> allCommentsByCommunityAndPerson(Community community, Person person) {

    final CriteriaBuilder cb = em.getCriteriaBuilder();
    final CriteriaQuery<Comment> cq = cb.createQuery(Comment.class);

    final Root<Comment> commentTable = cq.from(Comment.class);

    final List<Predicate> predicates = new ArrayList<>();

    if (community != null) {
      predicates.add(cb.equal(commentTable.get("community"), community));
    }

    if (person != null) {
      predicates.add(cb.equal(commentTable.get("person"), person));
    }

    cq.where(predicates.toArray(new Predicate[0]));

    return em.createQuery(cq).getResultList();
  }

  @Override
  public List<Comment> allCommentsByPerson(Person person) {

    final CriteriaBuilder cb = em.getCriteriaBuilder();
    final CriteriaQuery<Comment> cq = cb.createQuery(Comment.class);

    final Root<Comment> commentTable = cq.from(Comment.class);

    final List<Predicate> predicates = new ArrayList<>();

    if (person != null) {
      predicates.add(cb.equal(commentTable.get("person"), person));
    }

    cq.where(predicates.toArray(new Predicate[0]));

    return em.createQuery(cq).getResultList();

  }
}
