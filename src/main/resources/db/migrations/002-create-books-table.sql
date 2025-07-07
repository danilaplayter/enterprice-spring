-- 002-create-books-table.sql
-- Создание таблицы книг
CREATE
    TABLE
        mentee_power.books(
            id BIGSERIAL PRIMARY KEY,
            title VARCHAR(255) NOT NULL,
            author VARCHAR(255) NOT NULL,
            isbn VARCHAR(50) UNIQUE NOT NULL,
            published_date DATE NOT NULL,
            available BOOLEAN DEFAULT TRUE NOT NULL,
            created_at TIMESTAMP DEFAULT NOW() NOT NULL,
            updated_at TIMESTAMP DEFAULT NOW() NOT NULL
        );

-- Комментарии к таблице

COMMENT ON
TABLE
    mentee_power.books IS 'Каталог книг доступных в системе';

COMMENT ON
COLUMN mentee_power.books.title IS 'Название книги';

COMMENT ON
COLUMN mentee_power.books.author IS 'Автор книги';

COMMENT ON
COLUMN mentee_power.books.isbn IS 'Уникальный идентификатор книги (International Standard Book Number)';

COMMENT ON
COLUMN mentee_power.books.published_date IS 'Дата публикации книги';

COMMENT ON
COLUMN mentee_power.books.available IS 'Флаг доступности книги для выдачи';