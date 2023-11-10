INSERT INTO roles(name)
VALUES ('ROLE_USER'),
    ('ROLE_MODERATOR'),
    ('ROLE_ADMIN')
ON CONFLICT DO NOTHING;

INSERT INTO permissions(name, display_name)
VALUES ('DELETE_DISCUSSION', 'Delete a discussion'),
    ('DELETE_POST_COMMENT', 'Delete a comment on a post'),
    ('REPORT_POST_ABUSE', 'Report abuse in a post'),
    ('REPORT_DISCUSSION_ABUSE', 'Report abuse in a discussion'),
    ('VIEW_USER_DATA', 'View user information'),
    ('LIST_USERS', 'List and search the list of users.'),
    ('START_DISCUSSION', 'Start a discussion with a user'),
    ('SUSPEND_USER_ACCOUNT', 'Suspend a user account'),
    ('CLOSE_DISCUSSION', 'Close a discussion'),
    ('MANAGE_ROLES_PERMISSIONS', 'Manage roles and permissions'),
    ('DELETE_USER_ACCOUNT', 'Delete a user account')
ON CONFLICT DO NOTHING;
