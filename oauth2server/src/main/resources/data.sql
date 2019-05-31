/*INSERT INTO oauth_client_details
(client_id, resource_ids, client_secret, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove)
VALUES
('read-only-client', 'todo-services', NULL, 'read', 'implicit', 'http://localhost', NULL, 7200, 0, NULL, 'false');

INSERT INTO oauth_client_details
(client_id, resource_ids, client_secret, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove)
VALUES
('curl-client', 'todo-services', 'client-secret', 'read,write', 'client_credentials', '', 'role_admin', 7200, 0, NULL, 'false');

INSERT INTO oauth_client_details
(client_id, client_secret)
VALUES
('resource-server', 'resource-server');*/