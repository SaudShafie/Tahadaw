-- Tahadaw dev seed — Part 3 (Gift Journey: Message → Premium → Card)
-- Dev C: gift messages, history, premium payment, surprise plan, gift card
--
-- Load (in application-local.properties):
--   spring.sql.init.mode=always
--   spring.sql.init.data-locations=classpath:seed-part3.sql
--
-- Self-contained: does NOT require Part 2 API to be running.
-- Includes gift plans pre-filled at stages Part 3 flows expect.

-- Clear in FK-safe order
UPDATE gift_plan SET selected_gift_idea_id = NULL WHERE id IN (1, 2, 3);
DELETE FROM gift_message WHERE id = 1;
DELETE FROM selected_product WHERE id = 1;
DELETE FROM gift_idea_recommendation WHERE id IN (1, 2, 3);
DELETE FROM gift_plan WHERE id IN (1, 2, 3);
DELETE FROM premium_access WHERE id = 1;
DELETE FROM payment WHERE id = 1;
DELETE FROM recipient WHERE id = 1;
DELETE FROM `user` WHERE id IN (1, 2, 3);

-- Regular user (?userId=1) — messages, history, payment tests
INSERT INTO `user` (id, username, password, full_name, email, phone_number, role, is_premium, created_at, updated_at)
VALUES (1, 'devuser', 'placeholder', 'Dev User', 'dev@test.com', '+966501111111', 'USER', 0, NOW(), NOW());

-- Admin (optional reference)
INSERT INTO `user` (id, username, password, full_name, email, phone_number, role, is_premium, created_at, updated_at)
VALUES (2, 'admin', 'placeholder', 'Admin User', 'admin@test.com', NULL, 'ADMIN', 0, NOW(), NOW());

-- Premium user (?userId=3) — surprise plan + gift card without running payment first
INSERT INTO `user` (id, username, password, full_name, email, phone_number, role, is_premium, created_at, updated_at)
VALUES (3, 'premiumuser', 'placeholder', 'Premium User', 'premium@test.com', '+966502222222', 'USER', 1, NOW(), NOW());

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

-- Mock successful premium payment for user 3
INSERT INTO payment (id, user_id, amount_minor, currency, payment_type, status, provider, transaction_id, created_at)
VALUES (1, 3, 9900, 'SAR', 'PREMIUM', 'PAID', 'MOYASAR', 'seed-moyasar-payment-1', NOW());

INSERT INTO premium_access (id, user_id, payment_id, activated_at)
VALUES (1, 3, 1, NOW());

-- Gift plan 1: GIFT_IDEA_SELECTED (messages, surprise plan, gift card)
INSERT INTO gift_plan (
    id, user_id, recipient_id, occasion_type, occasion_date, budget_minor, currency,
    preferred_gift_style, language, status, recommendation_summary, selected_gift_idea_id,
    created_at, updated_at
)
VALUES (
    1, 1, 1, 'GRADUATION', '2026-06-20', 30000, 'SAR',
    'PRACTICAL', 'en', 'GIFT_IDEA_SELECTED', 'Practical graduation gifts for a gaming fan', NULL,
    NOW(), NOW()
);

INSERT INTO gift_idea_recommendation (
    id, gift_plan_id, title, category, price_band, reason,
    emotional_fit, practical_fit, risk_level, ai_explanation, search_keyword, is_selected, created_at
)
VALUES (
    1, 1, 'Wireless Gaming Headset', 'Electronics', 'MID',
    'Matches gaming interest and practical graduation style',
    'HIGH', 'HIGH', 'LOW',
    'He loves gaming and needs better audio for online play.',
    'wireless gaming headset under 300 SAR', 1, NOW()
);

UPDATE gift_plan SET selected_gift_idea_id = 1 WHERE id = 1;

-- Sample gift message for gift card tests (giftPlanId=1, giftMessageId=1)
INSERT INTO gift_message (id, gift_plan_id, tone, language, message_text, created_at)
VALUES (
    1, 1, 'warm', 'en',
    'Ahmed, congratulations on your graduation! Hope this gift makes your gaming sessions even better.',
    NOW()
);

-- Gift plan 2: PRODUCT_SELECTED (gift history tests)
INSERT INTO gift_plan (
    id, user_id, recipient_id, occasion_type, occasion_date, budget_minor, currency,
    preferred_gift_style, language, status, recommendation_summary, selected_gift_idea_id,
    created_at, updated_at
)
VALUES (
    2, 1, 1, 'BIRTHDAY', '2026-07-01', 25000, 'SAR',
    'PRACTICAL', 'en', 'PRODUCT_SELECTED', 'Birthday gift already narrowed to a product', NULL,
    NOW(), NOW()
);

INSERT INTO gift_idea_recommendation (
    id, gift_plan_id, title, category, price_band, reason,
    emotional_fit, practical_fit, risk_level, ai_explanation, search_keyword, is_selected, created_at
)
VALUES (
    2, 2, 'Coffee Brewing Kit', 'Kitchen', 'MID',
    'Fits coffee hobby and practical style',
    'MEDIUM', 'HIGH', 'LOW',
    'He enjoys brewing coffee at home.',
    'coffee brewing kit under 250 SAR', 1, NOW()
);

UPDATE gift_plan SET selected_gift_idea_id = 2 WHERE id = 2;

INSERT INTO selected_product (
    id, gift_idea_recommendation_id, title, price_minor, currency,
    image_url, product_url, source_name, rating, created_at
)
VALUES (
    1, 2, 'Manual Pour-Over Coffee Starter Set', 19900, 'SAR',
    'https://example.com/images/coffee-kit.jpg',
    'https://example.com/products/coffee-kit',
    'Example Store', 4.6, NOW()
);

-- Premium user copy of gift plan 1 for surprise/card with ?userId=3
INSERT INTO gift_plan (
    id, user_id, recipient_id, occasion_type, occasion_date, budget_minor, currency,
    preferred_gift_style, language, status, recommendation_summary, selected_gift_idea_id,
    created_at, updated_at
)
VALUES (
    3, 3, 1, 'GRADUATION', '2026-06-20', 30000, 'SAR',
    'PRACTICAL', 'en', 'GIFT_IDEA_SELECTED', 'Premium user test plan', NULL,
    NOW(), NOW()
);

INSERT INTO gift_idea_recommendation (
    id, gift_plan_id, title, category, price_band, reason,
    emotional_fit, practical_fit, risk_level, ai_explanation, search_keyword, is_selected, created_at
)
VALUES (
    3, 3, 'Wireless Gaming Headset', 'Electronics', 'MID',
    'Premium user seed recommendation',
    'HIGH', 'HIGH', 'LOW',
    'Seed data for premium surprise plan and gift card.',
    'wireless gaming headset under 300 SAR', 1, NOW()
);

UPDATE gift_plan SET selected_gift_idea_id = 3 WHERE id = 3;
