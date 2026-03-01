CREATE TABLE order_items (

    id UUID PRIMARY KEY,

    order_id UUID NOT NULL,

    amount NUMERIC(15,2) NOT NULL CHECK (amount > 0),

    item_type VARCHAR(50) NOT NULL,

    book_id UUID NOT NULL,

    chapter_id UUID NULL,

    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    updated_at TIMESTAMPTZ NOT NULL
);

-- Index for order lookup
CREATE INDEX idx_order_items_order_id
    ON order_items(order_id);

-- Index for book lookup
CREATE INDEX idx_order_items_book_id
    ON order_items(book_id);

-- Index for chapter lookup
CREATE INDEX idx_order_items_chapter_id
    ON order_items(chapter_id);

-- Prevent duplicate chapter purchase in same order
CREATE UNIQUE INDEX uk_order_item_unique_chapter
    ON order_items(order_id, chapter_id)
    WHERE chapter_id IS NOT NULL;
    
    
 
    
  --content-access  
CREATE TABLE user_content_access (

id UUID PRIMARY KEY,

user_id UUID NOT NULL,

access_type VARCHAR(50) NOT NULL,

book_id UUID NOT NULL,

chapter_id UUID NULL,

created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- Fast ownership checks
CREATE INDEX idx_access_user_book
    ON user_content_access(user_id, book_id);

CREATE INDEX idx_access_user_chapter
    ON user_content_access(user_id, chapter_id);

-- Prevent duplicate ownership
CREATE UNIQUE INDEX uk_access_unique_book
    ON user_content_access(user_id, book_id)
    WHERE chapter_id IS NULL;

CREATE UNIQUE INDEX uk_access_unique_chapter
    ON user_content_access(user_id, chapter_id)
    WHERE chapter_id IS NOT NULL;