package com.sublinks.sublinksapi.api.sublinks.v1.privatemessage.controllers;

import com.sublinks.sublinksapi.api.sublinks.v1.authentication.SublinksJwtPerson;
import com.sublinks.sublinksapi.api.sublinks.v1.common.controllers.AbstractSublinksApiController;
import com.sublinks.sublinksapi.api.sublinks.v1.privatemessage.models.DeletePrivateMessage;
import com.sublinks.sublinksapi.api.sublinks.v1.privatemessage.models.PrivateMessageResponse;
import com.sublinks.sublinksapi.api.sublinks.v1.privatemessage.services.SublinksPrivateMessageService;
import com.sublinks.sublinksapi.person.entities.Person;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/privatemessage/{key}/")
@Tag(name = "Privatemessage", description = "Privatemessage API")
public class SublinksPrivatemessageModeratorController extends AbstractSublinksApiController {

  private final SublinksPrivateMessageService sublinksPrivateMessageService;

  public SublinksPrivatemessageModeratorController(
      SublinksPrivateMessageService sublinksPrivateMessageService)
  {

    super();
    this.sublinksPrivateMessageService = sublinksPrivateMessageService;
  }

  @Operation(summary = "Purge an privatemessage")
  @DeleteMapping
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)})
  public PrivateMessageResponse purge(@PathVariable String key,
      final DeletePrivateMessage deletePrivateMessageForm,
      final SublinksJwtPerson sublinksJwtPerson)
  {

    final Person person = getPersonOrThrowBadRequest(sublinksJwtPerson);

    return sublinksPrivateMessageService.delete(key, deletePrivateMessageForm, person);
  }
}
