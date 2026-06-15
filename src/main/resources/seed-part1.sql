-- Tahadaw dev seed — Part 1 (Platform & Social)
-- Dev A: recipients, quality check, admin questions, notifications, reminders, group gifts
--
-- Load (in application-local.properties):
--   spring.sql.init.mode=always
--   spring.sql.init.data-locations=classpath:seed-part1.sql
--
-- Requires: app started once so Hibernate creates tables (spring.jpa.hibernate.ddl-auto=update)

-- Clear previous Part 1 seed rows (safe to re-run)
DELETE FROM recipient WHERE id = 1;
DELETE FROM `user` WHERE id IN (1, 2);

-- Test user (use ?userId=1 on all endpoints until auth is added)
INSERT INTO `user` (id, username, password, full_name, email, phone_number, role, is_premium, created_at, updated_at)
VALUES (1, 'devuser', 'placeholder', 'Dev User', 'dev@test.com', '+966501111111', 'USER', 0, NOW(), NOW());

-- Admin user (for Flow 17 admin endpoints during development)
INSERT INTO `user` (id, username, password, full_name, email, phone_number, role, is_premium, created_at, updated_at)
VALUES (2, 'admin', 'placeholder', 'Admin User', 'admin@test.com', NULL, 'ADMIN', 0, NOW(), NOW());

-- Recipient for quality check, group gifts, reminders
INSERT INTO recipient (
    id, user_id, name, relationship, age, gender,
    interests, hobbies, favorite_colors, favorite_brands, dislikes,
    personality_style, size_info, notes, consent_acknowledged, created_at, updated_at
)
VALUES (
    1, 1, 'Ahmed', 'Brother', 22, 'Male',
    'gaming, coffee, cars', 'FIFA, brewing', 'black, navy', 'Logitech, Sony', 'clothes, generic mugs',
    'practical', 'L shirt', 'Prefers useful gifts over decorative ones', 1, NOW(), NOW()
);
