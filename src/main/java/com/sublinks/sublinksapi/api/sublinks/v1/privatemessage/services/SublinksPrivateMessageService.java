package com.sublinks.sublinksapi.api.sublinks.v1.privatemessage.services;

import com.sublinks.sublinksapi.api.sublinks.v1.privatemessage.models.CreatePrivateMessage;
import com.sublinks.sublinksapi.api.sublinks.v1.privatemessage.models.DeletePrivateMessage;
import com.sublinks.sublinksapi.api.sublinks.v1.privatemessage.models.IndexPrivateMessages;
import com.sublinks.sublinksapi.api.sublinks.v1.privatemessage.models.MarkAsReadPrivateMessage;
import com.sublinks.sublinksapi.api.sublinks.v1.privatemessage.models.PrivateMessageResponse;
import com.sublinks.sublinksapi.api.sublinks.v1.privatemessage.models.UpdatePrivateMessage;
import com.sublinks.sublinksapi.authorization.enums.RolePermissionPrivateMessageTypes;
import com.sublinks.sublinksapi.authorization.services.AclService;
import com.sublinks.sublinksapi.person.entities.Person;
import com.sublinks.sublinksapi.post.repositories.PostRepository;
import com.sublinks.sublinksapi.privatemessages.entities.PrivateMessage;
import com.sublinks.sublinksapi.privatemessages.models.PrivateMessageSearchCriteria;
import com.sublinks.sublinksapi.privatemessages.repositories.PrivateMessageRepository;
import com.sublinks.sublinksapi.privatemessages.services.PrivateMessageService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@AllArgsConstructor
@Service
public class SublinksPrivateMessageService {


  private final PrivateMessageRepository privateMessageRepository;
  private final ConversionService conversionService;
  private final PostRepository postRepository;
  private final AclService aclService;
  private final PrivateMessageService privateMessageService;

  /**
   * Retrieves a list of private messages based on the given search criteria.
   *
   * @param indexPrivateMessagesForm The form containing the search criteria for filtering the
   *                                 private messages.
   * @param person                   The person accessing the private messages.
   * @return A list of PrivateMessageResponse objects containing the details of the private
   * messages.
   * @throws ResponseStatusException If the person is not allowed to read private messages.
   */
  public List<PrivateMessageResponse> index(final IndexPrivateMessages indexPrivateMessagesForm,
      final Person person)
  {

    aclService.canPerson(person)
        .performTheAction(RolePermissionPrivateMessageTypes.READ_PRIVATE_MESSAGES)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN,
            "not_allowed_to_read_private_messages"));

    return privateMessageRepository.allPrivateMessagesBySearchCriteria(
            PrivateMessageSearchCriteria.builder()
                .search(indexPrivateMessagesForm.search())
                .person(person)
                .unreadOnly(indexPrivateMessagesForm.unreadOnly() != null
                    && indexPrivateMessagesForm.unreadOnly())
                .privateMessageSortType(indexPrivateMessagesForm.sort())
                .page(indexPrivateMessagesForm.page())
                .perPage(indexPrivateMessagesForm.perPage())
                .build())
        .stream()
        .map(privateMessage -> conversionService.convert(privateMessage,
            PrivateMessageResponse.class))
        .collect(Collectors.toList());
  }

  /**
   * Retrieves the details of a private message.
   *
   * @param id     The ID of the private message.
   * @param person The person accessing the private message.
   * @return The PrivateMessageResponse object containing the details of the private message.
   * @throws ResponseStatusException If the person is not allowed to read private messages or if the
   *                                 private message is not found.
   */
  public PrivateMessageResponse show(final String id, final Person person) {

    aclService.canPerson(person)
        .performTheAction(RolePermissionPrivateMessageTypes.READ_PRIVATE_MESSAGES)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN,
            "not_allowed_to_read_private_messages"));

    final PrivateMessage privateMessage = privateMessageRepository.findById(Long.parseLong(id))
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
            "Image could not be written"));

    if (!privateMessage.getRecipient()
        .equals(person) && !privateMessage.getSender()
        .equals(person)) {
      return null;
    }

    return conversionService.convert(privateMessage, PrivateMessageResponse.class);
  }

  /**
   * Creates a new private message.
   *
   * @param createPrivateMessageForm The form containing the information of the new private
   *                                 message.
   * @param person                   The person creating the private message.
   * @return The newly created private message as a PrivateMessageResponse object.
   * @throws ResponseStatusException If the person is not allowed to send private messages.
   */
  public PrivateMessageResponse create(final CreatePrivateMessage createPrivateMessageForm,
      final Person person)
  {

    aclService.canPerson(person)
        .performTheAction(RolePermissionPrivateMessageTypes.CREATE_PRIVATE_MESSAGE)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN,
            "not_allowed_to_send_private_messages"));

    final PrivateMessage privateMessage = PrivateMessage.builder()
        .recipient(person)
        .sender(person)
        .content(createPrivateMessageForm.message())
        .isLocal(true)
        .build();

    privateMessageService.createPrivateMessage(privateMessage);

    return conversionService.convert(privateMessageRepository.save(privateMessage),
        PrivateMessageResponse.class);
  }

  /**
   * Updates a private message.
   *
   * @param updatePrivateMessageForm The form containing the updated private message information.
   * @param person                   The person performing the update.
   * @return The updated private message as a PrivateMessageResponse object.
   * @throws ResponseStatusException If the person is not allowed to update the private message or
   *                                 if the private message is not found.
   */
  public PrivateMessageResponse update(final UpdatePrivateMessage updatePrivateMessageForm,
      final Person person)
  {

    aclService.canPerson(person)
        .performTheAction(RolePermissionPrivateMessageTypes.UPDATE_PRIVATE_MESSAGE)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN,
            "not_allowed_to_update_private_messages"));

    final PrivateMessage privateMessage = privateMessageRepository.findById(
            Long.parseLong(updatePrivateMessageForm.privateMessageKey()))
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
            "Image could not be written"));

    if (!privateMessage.getRecipient()
        .equals(person) && !privateMessage.getSender()
        .equals(person)) {
      return null;
    }

    privateMessage.setContent(updatePrivateMessageForm.message());
    privateMessageService.updatePrivateMessage(privateMessage);

    return conversionService.convert(privateMessageRepository.save(privateMessage),
        PrivateMessageResponse.class);
  }

  /**
   * Deletes a private message.
   *
   * @param id                       The ID of the private message to be deleted.
   * @param deletePrivateMessageForm The form containing the deletion status to update.
   * @param person                   The person performing the deletion.
   * @return The response of the operation as a PrivateMessageResponse object.
   * @throws ResponseStatusException If the person is not allowed to delete the private message or
   *                                 if the private message is not found.
   */
  public PrivateMessageResponse delete(final String id,
      final DeletePrivateMessage deletePrivateMessageForm, final Person person)
  {

    aclService.canPerson(person)
        .performTheAction(RolePermissionPrivateMessageTypes.DELETE_PRIVATE_MESSAGE)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN,
            "not_allowed_to_delete_private_messages"));

    final PrivateMessage privateMessage = privateMessageRepository.findById(Long.parseLong(id))
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "private_message_not_found"));

    if (!privateMessage.getSender()
        .equals(person)) {
      return null;
    }

    privateMessage.setDeleted(deletePrivateMessageForm.removed());
    privateMessageService.updatePrivateMessage(privateMessage);

    return conversionService.convert(privateMessage, PrivateMessageResponse.class);
  }

  /**
   * Marks all private messages of a given person as read.
   *
   * @param person The person whose private messages should be marked as read.
   */
  public void markAllAsRead(final Person person) {

    aclService.canPerson(person)
        .performTheAction(RolePermissionPrivateMessageTypes.MARK_PRIVATE_MESSAGE_AS_READ)
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.FORBIDDEN, "not_allowed_to_mark_as_read"));

    List<PrivateMessage> privateMessages = privateMessageRepository.findByRecipientAndReadIsFalse(
        person);
    privateMessages.forEach(privateMessage -> {
      privateMessage.setRead(true);
      privateMessageService.updatePrivateMessage(privateMessage);
    });
  }

  /**
   * Marks a private message as read.
   *
   * @param id                           The ID of the private message to mark as read.
   * @param markAsReadPrivateMessageForm The form containing the read status to update.
   * @param person                       The person performing the action.
   * @return The updated private message as a PrivateMessageResponse object.
   * @throws ResponseStatusException If the person is not allowed to mark the private message as
   *                                 read or if the private message is not found.
   */
  public PrivateMessageResponse markAsRead(final String id,
      final MarkAsReadPrivateMessage markAsReadPrivateMessageForm, final Person person)
  {

    aclService.canPerson(person)
        .performTheAction(RolePermissionPrivateMessageTypes.MARK_PRIVATE_MESSAGE_AS_READ)
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.FORBIDDEN, "not_allowed_to_mark_as_read"));

    final PrivateMessage privateMessage = privateMessageRepository.findById(Long.parseLong(id))
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "private_message_not_found"));

    if (!privateMessage.getRecipient()
        .equals(person)) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "not_allowed_to_mark_as_read");
    }

    privateMessage.setRead(markAsReadPrivateMessageForm.read());
    privateMessageService.updatePrivateMessage(privateMessage);

    return conversionService.convert(privateMessage, PrivateMessageResponse.class);
  }

  /**
   * Purge a specific private message belonging to a person.
   *
   * @param person The person who owns the private message.
   * @param id     The ID of the private message to purge.
   * @return The response after purging the private message or null if the person is not authorized.
   * @throws ResponseStatusException If the person is not allowed to delete private messages or if
   *                                 the private message is not found.
   */
  public PrivateMessageResponse purgePrivateMessage(final Person person, final String id) {

    aclService.canPerson(person)
        .performTheAction(RolePermissionPrivateMessageTypes.PURGE_PRIVATE_MESSAGE)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN,
            "not_allowed_to_delete_private_messages"));

    final PrivateMessage privateMessage = privateMessageRepository.findById(Long.parseLong(id))
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "private_message_not_found"));

    if (!privateMessage.getRecipient()
        .equals(person) && !privateMessage.getSender()
        .equals(person)) {
      return null;
    }

    privateMessageService.deletePrivateMessage(privateMessage);

    return conversionService.convert(privateMessage, PrivateMessageResponse.class);
  }

  /**
   * Purges a list of private messages belonging to a specific person.
   *
   * @param ids    The list of IDs of the private messages to purge.
   * @param person The person who owns the private messages.
   * @return A list of PrivateMessageResponse objects representing the purged private messages.
   * @throws ResponseStatusException If the person is not allowed to delete private messages or if
   *                                 any of the private messages specified by the IDs are not found.
   */
  public List<PrivateMessageResponse> purgePrivateMessages(List<String> ids, final Person person) {

    aclService.canPerson(person)
        .performTheAction(RolePermissionPrivateMessageTypes.PURGE_PRIVATE_MESSAGES)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN,
            "not_allowed_to_delete_private_messages"));

    return privateMessageRepository.findAllById(ids.stream()
            .map(Long::parseLong)
            .collect(Collectors.toList()))
        .stream()
        .map(privateMessage -> {
          privateMessageService.deletePrivateMessage(privateMessage);
          return conversionService.convert(privateMessage, PrivateMessageResponse.class);
        })
        .collect(Collectors.toList());
  }

  /**
   * Purge all private messages belonging to a specific person.
   *
   * @param person The person for whom to purge all private messages.
   * @throws ResponseStatusException If the person is not allowed to delete private messages.
   */
  public List<PrivateMessageResponse> purgeAllPrivateMessages(final Person person) {

    aclService.canPerson(person)
        .performTheAction(RolePermissionPrivateMessageTypes.PURGE_PRIVATE_MESSAGE)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN,
            "not_allowed_to_delete_private_messages"));

    return privateMessageService.deleteAllPrivateMessagesByPerson(person, true)
        .stream()
        .map(privateMessage -> conversionService.convert(privateMessage,
            PrivateMessageResponse.class))
        .collect(Collectors.toList());
  }
}