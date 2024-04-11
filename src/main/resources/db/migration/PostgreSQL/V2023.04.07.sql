CREATE TABLE users (
  id BIGINT NOT NULL UNIQUE GENERATED ALWAYS AS IDENTITY,
  email VARCHAR(200),
  password VARCHAR(75),
  full_name VARCHAR(75),
  role VARCHAR(75),
  is_enabled boolean,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY(id)
);

-- INSERT INTO users(isEnabled, createdAt, email, fullName, password, role)
-- VALUES (true, NOW(), 'aman.sharma163@gmail.com', 'Aman Lalpuria','abcd1234', 'ADMIN')
--     ON CONFLICT DO NOTHING;
