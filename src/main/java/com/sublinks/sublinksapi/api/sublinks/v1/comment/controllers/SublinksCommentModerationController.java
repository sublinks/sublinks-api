package com.sublinks.sublinksapi.api.sublinks.v1.comment.controllers;

import com.sublinks.sublinksapi.api.sublinks.v1.authentication.SublinksJwtPerson;
import com.sublinks.sublinksapi.api.sublinks.v1.comment.models.CommentResponse;
import com.sublinks.sublinksapi.api.sublinks.v1.comment.models.Moderation.CommentDelete;
import com.sublinks.sublinksapi.api.sublinks.v1.comment.models.Moderation.CommentPin;
import com.sublinks.sublinksapi.api.sublinks.v1.comment.models.Moderation.CommentRemove;
import com.sublinks.sublinksapi.api.sublinks.v1.comment.services.SublinksCommentService;
import com.sublinks.sublinksapi.api.sublinks.v1.common.controllers.AbstractSublinksApiController;
import com.sublinks.sublinksapi.person.entities.Person;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/comment/{key}/moderation")
@Tag(name = "Comment Moderation", description = "Comment Moderation API")
public class SublinksCommentModerationController extends AbstractSublinksApiController {

  private final SublinksCommentService sublinksCommentService;

  @Operation(summary = "Remove a comment")
  @PostMapping("/remove")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)})
  public CommentResponse remove(@PathVariable final String key,
      @RequestBody @Valid final CommentRemove commentRemove,
      final SublinksJwtPerson sublinksJwtPerson) {

    final Person person = getPersonOrThrowUnauthorized(sublinksJwtPerson);

    return sublinksCommentService.remove(key, commentRemove, person);
  }

  @Operation(summary = "Delete a comment")
  @PostMapping("/delete")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)})
  public CommentResponse delete(@PathVariable final String key,
      @RequestBody @Valid final CommentDelete commentDeleteForm,
      final SublinksJwtPerson sublinksJwtPerson) {

    final Person person = getPersonOrThrowUnauthorized(sublinksJwtPerson);

    return sublinksCommentService.delete(key, commentDeleteForm, person);
  }

  @Operation(summary = "Pin/Unpin a comment")
  @GetMapping("/pin")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true)})
  public CommentResponse highlight(@PathVariable final String key, final CommentPin commentPinForm,
      final SublinksJwtPerson sublinksJwtPerson) {

    final Person person = getPersonOrThrowUnauthorized(sublinksJwtPerson);

    return sublinksCommentService.pin(key, commentPinForm, person);
  }
}
