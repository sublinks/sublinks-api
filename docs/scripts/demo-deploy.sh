#!/usr/bin/bash

# Pull any new images available
docker compose pull
# Restart all containers by destroying them and recreating
docker compose down
docker compose up -d

SLEEP=65
echo "Sleeping for ${SLEEP} seconds to allow system to boot..."
sleep ${SLEEP}

echo "Create initial admin user..."
read -r -d '' USERDATA <<EOF
{
  "username":"jgrim", 
  "password": "changeme1234", 
  "password_verify":"changeme1234"
}
EOF
TOKEN=$(curl -s -H "content-type: application/json" -H "accept: application/json" --data "${USERDATA}" "https://demo.sublinks.org/api/v3/user/register" | jq -r '.jwt')

echo "Initializing site..."
read -r -d '' SITEDATA <<EOF
{
  "name": "Sublinks",
  "sidebar": "",
  "description": "",
  "icon": "",
  "banner": "",
  "community_creation_admin_only": false,
  "enable_nsfw": true,
  "enable_downvotes": true,
  "application_question": "",
  "registration_mode": "Open",
  "require_email_verification": false,
  "private_instance": true,
  "default_theme": "",
  "default_post_listing_type": "All",
  "application_email_admins": false,
  "hide_modlog_mod_names": false,
  "legal_information": "",
  "slur_filter_regex": "",
  "actor_name_max_length": 20,
  "federation_enabled": false,
  "captcha_enabled": false,
  "captcha_difficulty": "",
  "discussion_languages": [1,38]
}
EOF
RES=$(curl -s -H "Authorization: Bearer ${TOKEN}" -H "content-type: application/json" -H "accept: application/json" --data "${SITEDATA}" "https://demo.sublinks.org/api/v3/site")

echo "Creating test community..."
read -r -d '' COMMUNITY <<EOF
{
  "name": "test_community",
  "title": "Test Community",
  "discussion_languages": [1,38]
}
EOF
COMMUNITY_ID=$(curl -s -H "Authorization: Bearer ${TOKEN}" -H "content-type: application/json" -H "accept: application/json" --data "${COMMUNITY}" "https://demo.sublinks.org/api/v3/community" | jq '.community_view.community.id')

echo "Creating test post in test community..."
read -r -d '' POST <<EOF
{
  "name": "Test Post",
  "body": "This is a test post in a test community",
  "language_id": 38,
  "community_id": ${COMMUNITY_ID}
}
EOF
POST_ID=$(curl -s -H "Authorization: Bearer ${TOKEN}" -H "content-type: application/json" -H "accept: application/json" --data "${POST}" "https://demo.sublinks.org/api/v3/post" | jq -r '.post_view.post.id')

echo "Creating test comment on test post in test community..."
read -r -d '' COMMENT <<EOF
{
  "post_id": ${POST_ID},
  "language_id": 38,
  "content": "This is a test comment on a test post in a test community"
}
EOF
COMMENT_ID=$(curl -s -H "Authorization: Bearer ${TOKEN}" -H "content-type: application/json" -H "accept: application/json" --data "${COMMENT}" "https://demo.sublinks.org/api/v3/comment" | jq -r '.comment_view.comment.id')

echo "Creating reply to test comment on test post in test community..."
read -r -d '' REPLY <<EOF
{
  "parent_id": ${COMMENT_ID},
  "post_id": ${POST_ID},
  "language_id": 38,
  "content": "This is a reply to a test comment on a test post in a test community"
}
EOF
REPLY_ID=$(curl -s -H "Authorization: Bearer ${TOKEN}" -H "content-type: application/json" -H "accept: application/json" --data "${REPLY}" "https://demo.sublinks.org/api/v3/comment" | jq -r '.comment_view.comment.id')

