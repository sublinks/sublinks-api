package com.sublinks.sublinksapi.privatemessages.models;

import com.sublinks.sublinksapi.comment.entities.CommentReply;
import com.sublinks.sublinksapi.person.entities.PersonMention;
import com.sublinks.sublinksapi.privatemessages.entities.PrivateMessage;
import lombok.Builder;
import java.util.List;

@Builder
public record MarkAllAsReadResponse(
    List<PrivateMessage> privateMessages,
    List<CommentReply> commentReplies,
    List<PersonMention> personMentions
) {

}
