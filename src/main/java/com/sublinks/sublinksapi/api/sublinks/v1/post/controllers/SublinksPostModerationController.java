package com.sublinks.sublinksapi.api.sublinks.v1.post.controllers;

import com.sublinks.sublinksapi.api.sublinks.v1.authentication.SublinksJwtPerson;
import com.sublinks.sublinksapi.api.sublinks.v1.common.controllers.AbstractSublinksApiController;
import com.sublinks.sublinksapi.api.sublinks.v1.post.models.moderation.RemovePost;
import com.sublinks.sublinksapi.api.sublinks.v1.post.services.SublinksPostService;
import com.sublinks.sublinksapi.person.entities.Person;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/post/{key}/moderation")
@Tag(name = "Post", description = "Post API")
@AllArgsConstructor
public class SublinksPostModerationController extends AbstractSublinksApiController {

  private final SublinksPostService sublinksPostService;

  @Operation(summary = "Remove a post")
  @PostMapping
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)})
  public void remove(@PathVariable final String key, @RequestBody final RemovePost removePostForm,
      final SublinksJwtPerson sublinksJwtPerson)
  {

    final Person person = getPersonOrThrowUnauthorized(sublinksJwtPerson);

    sublinksPostService.remove(key, removePostForm, person);
  }


}
