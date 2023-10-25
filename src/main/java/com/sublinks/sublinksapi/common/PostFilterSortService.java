package com.sublinks.sublinksapi.common;

import com.sublinks.sublinksapi.person.Person;
import com.sublinks.sublinksapi.person.enums.ListingType;
import com.sublinks.sublinksapi.person.enums.SortType;
import com.sublinks.sublinksapi.post.Post;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class PostFilterSortService {

    @Getter
    @Setter
    public static class FetchRequest{
        private SortType sortType;
        private ListingType listingType;
        private int perPage;
        private int page;
        private List<Long> communityIds;
        private Person person;
        private boolean isSavedOnly;
        private boolean isDislikedOnly;
    }

    public Collection<Post> fetch(FetchRequest fetchRequest) {

        return null;
    }

    private void getNewQuery() {

    }

    private void joinAggregateTable() {

    }

    private void filterByCommunityIds() {

    }

    private void filterByIsSavedOrDislikedOnly() {

    }

    private void filterByListingType() {

    }

    private void sortBySortType() {

    }

    private void setLimits() {

    }
}
