CREATE TABLE IF NOT EXISTS nuverse.refresh_tokens (
                               id BIGSERIAL PRIMARY KEY,
                               token TEXT NOT NULL UNIQUE,
                               expiry_date TIMESTAMP NOT NULL,
                               user_id UUID NOT NULL UNIQUE,
                               FOREIGN KEY (user_id) REFERENCES nuverse.users(id) ON DELETE CASCADE
);
