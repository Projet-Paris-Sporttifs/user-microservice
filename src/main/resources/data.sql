INSERT INTO roles(id, name)
VALUES (1, 'ROLE_USER'),
    (2, 'ROLE_MODERATOR'),
    (3, 'ROLE_ADMIN')
ON CONFLICT DO NOTHING;

INSERT INTO permissions(id, name, display_name)
VALUES (1, 'DELETE_DISCUSSION', 'Delete a discussion'),
    (2, 'DELETE_POST_COMMENT', 'Delete a comment on a post'),
    (3, 'REPORT_POST_ABUSE', 'Report abuse in a post'),
    (4, 'REPORT_DISCUSSION_ABUSE', 'Report abuse in a discussion'),
    (5, 'VIEW_USER_DATA', 'View user information'),
    (6, 'LIST_USERS', 'List and search the list of users.'),
    (7, 'START_CHAT', 'Start a chat with a user'),
    (8, 'SUSPEND_USER_ACCOUNT', 'Suspend a user account'),
    (9, 'CLOSE_DISCUSSION', 'Close a discussion'),
    (10, 'MANAGE_ROLES_PERMISSIONS', 'Manage roles and permissions'),
    (11, 'DELETE_USER_ACCOUNT', 'Delete a user account'),
    (12, 'CREATE_DISCUSSION', 'Create a discussion')
ON CONFLICT DO NOTHING;

INSERT INTO roles_permissions(role_id, permission_id)
    VALUES (1, 7),
    (1, 3),
    (1, 12),
    (2, 1),
    (2, 2),
    (2, 3),
    (2, 4),
    (2, 5),
    (2, 6),
    (2, 8),
    (2, 9),
    (3, 10),
    (3, 11)
ON CONFLICT DO NOTHING;
