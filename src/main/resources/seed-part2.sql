-- Tahadaw dev seed — Part 2 (Gift Journey: Plan → Product)
-- Dev B: gift plans, required Q, AI Q, AI recommendations, product search
--
-- Load (in application-local.properties):
--   spring.sql.init.mode=always
--   spring.sql.init.data-locations=classpath:seed-part2.sql
--
-- Self-contained: includes Part 1 base rows + admin required questions.
-- Create gift plans through the API during testing (no pre-seeded gift plan).

DELETE FROM required_question WHERE id IN (1, 2);
DELETE FROM recipient WHERE id = 1;
DELETE FROM `user` WHERE id IN (1, 2);

INSERT INTO `user` (id, username, password, full_name, email, phone_number, role, is_premium, created_at, updated_at)
VALUES (1, 'devuser', 'placeholder', 'Dev User', 'dev@test.com', '+966501111111', 'USER', 0, NOW(), NOW());

INSERT INTO `user` (id, username, password, full_name, email, phone_number, role, is_premium, created_at, updated_at)
VALUES (2, 'admin', 'placeholder', 'Admin User', 'admin@test.com', NULL, 'ADMIN', 0, NOW(), NOW());

INSERT INTO recipient (
    id, user_id, name, relationship, age, gender,
    interests, hobbies, favorite_colors, favorite_brands, dislikes,
    personality_style, size_info, notes, consent_acknowledged, created_at, updated_at
)
VALUES (
    1, 1, 'Ahmed', 'Brother', 22, 'Male',
    'gaming, coffee, cars', 'FIFA, brewing', 'black, navy', 'Logitech, Sony', 'clothes, generic mugs',
    'practical', 'L shirt', 'Prefers useful gifts', 1, NOW(), NOW()
);

-- Admin required questions (Flow 4 — also used when testing via API)
INSERT INTO required_question (id, question_text, question_type, is_active, display_order, created_at, updated_at)
VALUES
    (3, 'How close is your relationship with this person?', 'TEXT', 1,  NOW(), NOW()),
    (1, 'What gift style fits this occasion best?', 'TEXT', 1,  NOW(), NOW()),
    (1, 'What is the gift budget mindset?', 'TEXT', 1,  NOW(), NOW());
