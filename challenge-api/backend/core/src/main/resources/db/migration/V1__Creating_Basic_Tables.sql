CREATE TABLE "user"
(
	"id"                  BIGINT        NOT NULL,
	"enabled"             BOOLEAN       NOT NULL,
	"password"            VARCHAR(100)  NOT NULL,
	"name"                VARCHAR(100)  NOT NULL,
	"cpf"                 VARCHAR(11)   NOT NULL,
	"user_group"          VARCHAR(15)   NOT NULL,
	"phones"              jsonb         NOT NULL,
    "emails"              jsonb         NOT NULL,
	PRIMARY KEY ("id")
);

CREATE TABLE "address"
(
	"id"           BIGINT           NOT NULL,
	"city"         VARCHAR(100),
	"complement"   VARCHAR(100),
	"description"  VARCHAR(200),
	"state"        VARCHAR(2),
	"zip_code"     VARCHAR(9),
	"user_id"      BIGINT,
	"neighborhood" VARCHAR(50),
	"number"       VARCHAR(100),
	PRIMARY KEY ("id")
);

INSERT INTO public."user" (id,  enabled, "name", password, user_group, cpf, emails, phones)
VALUES (1,   true,
        'admin','$2a$12$EYp3i6ZHsQ/tDHuSFXVxjeM1WCveEjPE56hNGyentG78tCzjIIZrG', 'ADMIN',
        11111111111, '{"emails" : "admin@admin.com"}','[{"Residencial": "35323738"}]'); --password: 123456

INSERT INTO public."user" (id,  enabled, "name", password, user_group, cpf, emails, phones)
VALUES (2,  true,
        'comum','$2a$12$EYp3i6ZHsQ/tDHuSFXVxjeM1WCveEjPE56hNGyentG78tCzjIIZrG', 'READER',
        22222222222, '{"emails" : "comum@comum.com"}','[{"Comercial": "981063532"}]'); --password: 123456

ALTER TABLE if EXISTS "user"
ADD CONSTRAINT UK_CPF UNIQUE ("cpf");

ALTER TABLE if EXISTS "user"
ADD CONSTRAINT UK_NAME UNIQUE ("name");

ALTER TABLE IF EXISTS "address"
ADD CONSTRAINT "fk_user_address_user"
FOREIGN KEY ("user_id") REFERENCES "user";

create sequence seq_user START 3;
create sequence seq_address START 1;
