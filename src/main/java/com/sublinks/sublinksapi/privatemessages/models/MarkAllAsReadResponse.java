package com.sublinks.sublinksapi.privatemessages.models;

import com.sublinks.sublinksapi.comment.entities.CommentReply;
import com.sublinks.sublinksapi.person.entities.PersonMention;
import com.sublinks.sublinksapi.privatemessages.entities.PrivateMessage;
import java.util.List;
import lombok.Builder;

@Builder
public record MarkAllAsReadResponse(
    List<PrivateMessage> privateMessages,
    List<CommentReply> commentReplies,
    List<PersonMention> personMentions
) {

}
