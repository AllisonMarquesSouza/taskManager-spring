CREATE TABLE tasks(
    id UUID PRIMARY KEY ,
    user_id INTEGER NOT NULL,
    name VARCHAR(155) NOT NULL ,
    description TEXT,
    priority VARCHAR(50), --low, medium, high
    status VARCHAR(50) NOT NULL , --pending, in-progress, completed
    due_date DATE,
    created_at TIMESTAMP NOT NULL,
    update_at TIMESTAMP,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
)