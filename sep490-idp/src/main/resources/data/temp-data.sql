INSERT INTO enterprise (id) VALUES ('550e8400-e29b-41d4-a716-446655440001');
INSERT INTO enterprise (id) VALUES ('550e8400-e29b-41d4-a716-446655440002');
INSERT INTO enterprise (id) VALUES ('550e8400-e29b-41d4-a716-446655440003');
INSERT INTO enterprise (id) VALUES ('550e8400-e29b-41d4-a716-446655440004');
INSERT INTO enterprise (id) VALUES ('550e8400-e29b-41d4-a716-446655440005');

INSERT INTO building (id, enterprise_id) VALUES ('660e8400-e29b-41d4-a716-446655440003', '550e8400-e29b-41d4-a716-446655440001');
INSERT INTO building (id, enterprise_id) VALUES ('660e8400-e29b-41d4-a716-446655440004', '550e8400-e29b-41d4-a716-446655440001');
INSERT INTO building (id, enterprise_id) VALUES ('660e8400-e29b-41d4-a716-446655440005', '550e8400-e29b-41d4-a716-446655440001');

INSERT INTO building (id, enterprise_id) VALUES ('660e8400-e29b-41d4-a716-446655440006', '550e8400-e29b-41d4-a716-446655440002');
INSERT INTO building (id, enterprise_id) VALUES ('660e8400-e29b-41d4-a716-446655440007', '550e8400-e29b-41d4-a716-446655440002');
INSERT INTO building (id, enterprise_id) VALUES ('660e8400-e29b-41d4-a716-446655440008', '550e8400-e29b-41d4-a716-446655440002');

INSERT INTO building (id, enterprise_id) VALUES ('660e8400-e29b-41d4-a716-446655440000', '550e8400-e29b-41d4-a716-446655440003');
INSERT INTO building (id, enterprise_id) VALUES ('660e8400-e29b-41d4-a716-446655440001', '550e8400-e29b-41d4-a716-446655440003');
INSERT INTO building (id, enterprise_id) VALUES ('660e8400-e29b-41d4-a716-446655440002', '550e8400-e29b-41d4-a716-446655440003');

INSERT INTO building_permission (building_id, user_id, role) VALUES ('660e8400-e29b-41d4-a716-446655440003', '5ed84416-2e1f-4e01-afe3-5712d84e170c', 'MANAGER');
INSERT INTO building_permission (building_id, user_id, role) VALUES ('660e8400-e29b-41d4-a716-446655440004', '5ed84416-2e1f-4e01-afe3-5712d84e170c', 'MANAGER');
INSERT INTO building_permission (building_id, user_id, role) VALUES ('660e8400-e29b-41d4-a716-446655440005', '5ed84416-2e1f-4e01-afe3-5712d84e170c', 'MANAGER');

INSERT INTO credit_convert_ratio (
    created_date, last_modified_date, created_by, last_modified_by,
    id, version, ratio, convert_type
) VALUES
    (NOW(), NOW(), 'tran gia bao', 'tran gia bao', gen_random_uuid(), 0, 1, 'MONTH'),
    (NOW(), NOW(), 'tran gia bao', 'tran gia bao', gen_random_uuid(), 0, 0.1, 'DEVICE');
