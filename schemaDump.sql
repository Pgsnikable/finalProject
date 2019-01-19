--
-- PostgreSQL database dump
--

-- Dumped from database version 11.1
-- Dumped by pg_dump version 11.1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: pharmacy_storage; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA pharmacy_storage;


ALTER SCHEMA pharmacy_storage OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: category; Type: TABLE; Schema: pharmacy_storage; Owner: postgres
--

CREATE TABLE pharmacy_storage.category (
    id integer NOT NULL,
    category_name character varying(64) NOT NULL
);


ALTER TABLE pharmacy_storage.category OWNER TO postgres;

--
-- Name: category_id_seq; Type: SEQUENCE; Schema: pharmacy_storage; Owner: postgres
--

CREATE SEQUENCE pharmacy_storage.category_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE pharmacy_storage.category_id_seq OWNER TO postgres;

--
-- Name: category_id_seq; Type: SEQUENCE OWNED BY; Schema: pharmacy_storage; Owner: postgres
--

ALTER SEQUENCE pharmacy_storage.category_id_seq OWNED BY pharmacy_storage.category.id;


--
-- Name: drug; Type: TABLE; Schema: pharmacy_storage; Owner: postgres
--

CREATE TABLE pharmacy_storage.drug (
    id integer NOT NULL,
    name character varying(128) NOT NULL,
    composition character varying(256) NOT NULL,
    indications character varying(256) NOT NULL,
    mode_of_application character varying(256) NOT NULL,
    contraindications character varying(256) NOT NULL,
    recipe_needed boolean NOT NULL,
    category_id integer,
    storage_quantity integer NOT NULL,
    price numeric NOT NULL
);


ALTER TABLE pharmacy_storage.drug OWNER TO postgres;

--
-- Name: drug_dosage; Type: TABLE; Schema: pharmacy_storage; Owner: postgres
--

CREATE TABLE pharmacy_storage.drug_dosage (
    id integer NOT NULL,
    drug_id integer,
    dosage character varying(64) NOT NULL
);


ALTER TABLE pharmacy_storage.drug_dosage OWNER TO postgres;

--
-- Name: drug_dosage_id_seq; Type: SEQUENCE; Schema: pharmacy_storage; Owner: postgres
--

CREATE SEQUENCE pharmacy_storage.drug_dosage_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE pharmacy_storage.drug_dosage_id_seq OWNER TO postgres;

--
-- Name: drug_dosage_id_seq; Type: SEQUENCE OWNED BY; Schema: pharmacy_storage; Owner: postgres
--

ALTER SEQUENCE pharmacy_storage.drug_dosage_id_seq OWNED BY pharmacy_storage.drug_dosage.id;


--
-- Name: drug_id_seq; Type: SEQUENCE; Schema: pharmacy_storage; Owner: postgres
--

CREATE SEQUENCE pharmacy_storage.drug_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE pharmacy_storage.drug_id_seq OWNER TO postgres;

--
-- Name: drug_id_seq; Type: SEQUENCE OWNED BY; Schema: pharmacy_storage; Owner: postgres
--

ALTER SEQUENCE pharmacy_storage.drug_id_seq OWNED BY pharmacy_storage.drug.id;


--
-- Name: payment_data; Type: TABLE; Schema: pharmacy_storage; Owner: postgres
--

CREATE TABLE pharmacy_storage.payment_data (
    id integer NOT NULL,
    user_id integer,
    balance numeric,
    card_number bigint NOT NULL
);


ALTER TABLE pharmacy_storage.payment_data OWNER TO postgres;

--
-- Name: payment_data_id_seq; Type: SEQUENCE; Schema: pharmacy_storage; Owner: postgres
--

CREATE SEQUENCE pharmacy_storage.payment_data_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE pharmacy_storage.payment_data_id_seq OWNER TO postgres;

--
-- Name: payment_data_id_seq; Type: SEQUENCE OWNED BY; Schema: pharmacy_storage; Owner: postgres
--

ALTER SEQUENCE pharmacy_storage.payment_data_id_seq OWNED BY pharmacy_storage.payment_data.id;


--
-- Name: recipe; Type: TABLE; Schema: pharmacy_storage; Owner: postgres
--

CREATE TABLE pharmacy_storage.recipe (
    id integer NOT NULL,
    user_id integer,
    dosage_id integer,
    expiration_date date NOT NULL
);


ALTER TABLE pharmacy_storage.recipe OWNER TO postgres;

--
-- Name: recipe_id_seq; Type: SEQUENCE; Schema: pharmacy_storage; Owner: postgres
--

CREATE SEQUENCE pharmacy_storage.recipe_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE pharmacy_storage.recipe_id_seq OWNER TO postgres;

--
-- Name: recipe_id_seq; Type: SEQUENCE OWNED BY; Schema: pharmacy_storage; Owner: postgres
--

ALTER SEQUENCE pharmacy_storage.recipe_id_seq OWNED BY pharmacy_storage.recipe.id;


--
-- Name: recipe_request; Type: TABLE; Schema: pharmacy_storage; Owner: postgres
--

CREATE TABLE pharmacy_storage.recipe_request (
    id integer NOT NULL,
    user_id integer NOT NULL,
    drug_dosage_id integer NOT NULL,
    expiration_date date NOT NULL,
    request_status integer NOT NULL
);


ALTER TABLE pharmacy_storage.recipe_request OWNER TO postgres;

--
-- Name: recipe_request_id_seq; Type: SEQUENCE; Schema: pharmacy_storage; Owner: postgres
--

CREATE SEQUENCE pharmacy_storage.recipe_request_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE pharmacy_storage.recipe_request_id_seq OWNER TO postgres;

--
-- Name: recipe_request_id_seq; Type: SEQUENCE OWNED BY; Schema: pharmacy_storage; Owner: postgres
--

ALTER SEQUENCE pharmacy_storage.recipe_request_id_seq OWNED BY pharmacy_storage.recipe_request.id;


--
-- Name: user_account; Type: TABLE; Schema: pharmacy_storage; Owner: postgres
--

CREATE TABLE pharmacy_storage.user_account (
    id integer NOT NULL,
    login character varying(32) NOT NULL,
    password character varying(32) NOT NULL,
    role integer NOT NULL
);


ALTER TABLE pharmacy_storage.user_account OWNER TO postgres;

--
-- Name: user_account_id_seq; Type: SEQUENCE; Schema: pharmacy_storage; Owner: postgres
--

CREATE SEQUENCE pharmacy_storage.user_account_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE pharmacy_storage.user_account_id_seq OWNER TO postgres;

--
-- Name: user_account_id_seq; Type: SEQUENCE OWNED BY; Schema: pharmacy_storage; Owner: postgres
--

ALTER SEQUENCE pharmacy_storage.user_account_id_seq OWNED BY pharmacy_storage.user_account.id;


--
-- Name: user_data; Type: TABLE; Schema: pharmacy_storage; Owner: postgres
--

CREATE TABLE pharmacy_storage.user_data (
    id integer NOT NULL,
    user_id integer,
    first_name character varying(16) NOT NULL,
    last_name character varying(16) NOT NULL,
    date_of_birth date NOT NULL,
    email character varying(32)
);


ALTER TABLE pharmacy_storage.user_data OWNER TO postgres;

--
-- Name: user_data_id_seq; Type: SEQUENCE; Schema: pharmacy_storage; Owner: postgres
--

CREATE SEQUENCE pharmacy_storage.user_data_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE pharmacy_storage.user_data_id_seq OWNER TO postgres;

--
-- Name: user_data_id_seq; Type: SEQUENCE OWNED BY; Schema: pharmacy_storage; Owner: postgres
--

ALTER SEQUENCE pharmacy_storage.user_data_id_seq OWNED BY pharmacy_storage.user_data.id;


--
-- Name: user_order; Type: TABLE; Schema: pharmacy_storage; Owner: postgres
--

CREATE TABLE pharmacy_storage.user_order (
    id integer NOT NULL,
    user_id integer,
    dosage_id integer,
    quantity integer NOT NULL,
    order_time timestamp without time zone,
    order_cost numeric NOT NULL,
    order_status integer NOT NULL
);


ALTER TABLE pharmacy_storage.user_order OWNER TO postgres;

--
-- Name: user_order_id_seq; Type: SEQUENCE; Schema: pharmacy_storage; Owner: postgres
--

CREATE SEQUENCE pharmacy_storage.user_order_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE pharmacy_storage.user_order_id_seq OWNER TO postgres;

--
-- Name: user_order_id_seq; Type: SEQUENCE OWNED BY; Schema: pharmacy_storage; Owner: postgres
--

ALTER SEQUENCE pharmacy_storage.user_order_id_seq OWNED BY pharmacy_storage.user_order.id;


--
-- Name: category id; Type: DEFAULT; Schema: pharmacy_storage; Owner: postgres
--

ALTER TABLE ONLY pharmacy_storage.category ALTER COLUMN id SET DEFAULT nextval('pharmacy_storage.category_id_seq'::regclass);


--
-- Name: drug id; Type: DEFAULT; Schema: pharmacy_storage; Owner: postgres
--

ALTER TABLE ONLY pharmacy_storage.drug ALTER COLUMN id SET DEFAULT nextval('pharmacy_storage.drug_id_seq'::regclass);


--
-- Name: drug_dosage id; Type: DEFAULT; Schema: pharmacy_storage; Owner: postgres
--

ALTER TABLE ONLY pharmacy_storage.drug_dosage ALTER COLUMN id SET DEFAULT nextval('pharmacy_storage.drug_dosage_id_seq'::regclass);


--
-- Name: payment_data id; Type: DEFAULT; Schema: pharmacy_storage; Owner: postgres
--

ALTER TABLE ONLY pharmacy_storage.payment_data ALTER COLUMN id SET DEFAULT nextval('pharmacy_storage.payment_data_id_seq'::regclass);


--
-- Name: recipe id; Type: DEFAULT; Schema: pharmacy_storage; Owner: postgres
--

ALTER TABLE ONLY pharmacy_storage.recipe ALTER COLUMN id SET DEFAULT nextval('pharmacy_storage.recipe_id_seq'::regclass);


--
-- Name: recipe_request id; Type: DEFAULT; Schema: pharmacy_storage; Owner: postgres
--

ALTER TABLE ONLY pharmacy_storage.recipe_request ALTER COLUMN id SET DEFAULT nextval('pharmacy_storage.recipe_request_id_seq'::regclass);


--
-- Name: user_account id; Type: DEFAULT; Schema: pharmacy_storage; Owner: postgres
--

ALTER TABLE ONLY pharmacy_storage.user_account ALTER COLUMN id SET DEFAULT nextval('pharmacy_storage.user_account_id_seq'::regclass);


--
-- Name: user_data id; Type: DEFAULT; Schema: pharmacy_storage; Owner: postgres
--

ALTER TABLE ONLY pharmacy_storage.user_data ALTER COLUMN id SET DEFAULT nextval('pharmacy_storage.user_data_id_seq'::regclass);


--
-- Name: user_order id; Type: DEFAULT; Schema: pharmacy_storage; Owner: postgres
--

ALTER TABLE ONLY pharmacy_storage.user_order ALTER COLUMN id SET DEFAULT nextval('pharmacy_storage.user_order_id_seq'::regclass);


--
-- Data for Name: category; Type: TABLE DATA; Schema: pharmacy_storage; Owner: postgres
--

COPY pharmacy_storage.category (id, category_name) FROM stdin;
1	Витамины
2	Антикоагулянты
4	Препараты для лечения заболевания сердца
8	Миорелаксанты перифирического действия
10	Слабительные препараты
11	Противоопухолевые препараты
12	Препараты для лечения нервной системы
\.


--
-- Data for Name: drug; Type: TABLE DATA; Schema: pharmacy_storage; Owner: postgres
--

COPY pharmacy_storage.drug (id, name, composition, indications, mode_of_application, contraindications, recipe_needed, category_id, storage_quantity, price) FROM stdin;
2	Метотрексат	Активное вещество: метотрексат (в виде метотрексата натрия)	Онкологические заболевания, псориаз, ревматоидный артрит	Метотрексат применяют внутрь и парентерально: вводят внутримышечно, внутривенно, внутриартериально, интратектально	Противопоказан при следующих состояниях: нарушения функции печени, злоупотребеление алкоголем, нарушение функции почек, тяжелые инфекции, язвы ротовой полости	t	11	167	9.00
\.


--
-- Data for Name: drug_dosage; Type: TABLE DATA; Schema: pharmacy_storage; Owner: postgres
--

COPY pharmacy_storage.drug_dosage (id, drug_id, dosage) FROM stdin;
1	2	Таблетки 2,5 мг №10х2
2	2	Таблетки 5 мг №10х2
3	2	Раствор для инъекций 10 мг
4	2	Раствор для инъекций 50 мг
\.


--
-- Data for Name: payment_data; Type: TABLE DATA; Schema: pharmacy_storage; Owner: postgres
--

COPY pharmacy_storage.payment_data (id, user_id, balance, card_number) FROM stdin;
2	4	63.00	1234567812345678
\.


--
-- Data for Name: recipe; Type: TABLE DATA; Schema: pharmacy_storage; Owner: postgres
--

COPY pharmacy_storage.recipe (id, user_id, dosage_id, expiration_date) FROM stdin;
3	4	3	2019-02-01
\.


--
-- Data for Name: recipe_request; Type: TABLE DATA; Schema: pharmacy_storage; Owner: postgres
--

COPY pharmacy_storage.recipe_request (id, user_id, drug_dosage_id, expiration_date, request_status) FROM stdin;
3	4	3	2019-02-01	0
\.


--
-- Data for Name: user_account; Type: TABLE DATA; Schema: pharmacy_storage; Owner: postgres
--

COPY pharmacy_storage.user_account (id, login, password, role) FROM stdin;
3	pgsnikable	=?UTF-8?B?QTEyMw==?=	1
4	pgsnik	=?UTF-8?B?QTEyMw==?=	0
7	savishna	=?UTF-8?B?QTEyMw==?=	2
\.


--
-- Data for Name: user_data; Type: TABLE DATA; Schema: pharmacy_storage; Owner: postgres
--

COPY pharmacy_storage.user_data (id, user_id, first_name, last_name, date_of_birth, email) FROM stdin;
2	4	Egor	Koltsov	1991-07-12	pgsnik@mail.ru
\.


--
-- Data for Name: user_order; Type: TABLE DATA; Schema: pharmacy_storage; Owner: postgres
--

COPY pharmacy_storage.user_order (id, user_id, dosage_id, quantity, order_time, order_cost, order_status) FROM stdin;
7	4	3	3	2019-01-16 23:57:43.423	27.00	0
\.


--
-- Name: category_id_seq; Type: SEQUENCE SET; Schema: pharmacy_storage; Owner: postgres
--

SELECT pg_catalog.setval('pharmacy_storage.category_id_seq', 12, true);


--
-- Name: drug_dosage_id_seq; Type: SEQUENCE SET; Schema: pharmacy_storage; Owner: postgres
--

SELECT pg_catalog.setval('pharmacy_storage.drug_dosage_id_seq', 10, true);


--
-- Name: drug_id_seq; Type: SEQUENCE SET; Schema: pharmacy_storage; Owner: postgres
--

SELECT pg_catalog.setval('pharmacy_storage.drug_id_seq', 6, true);


--
-- Name: payment_data_id_seq; Type: SEQUENCE SET; Schema: pharmacy_storage; Owner: postgres
--

SELECT pg_catalog.setval('pharmacy_storage.payment_data_id_seq', 2, true);


--
-- Name: recipe_id_seq; Type: SEQUENCE SET; Schema: pharmacy_storage; Owner: postgres
--

SELECT pg_catalog.setval('pharmacy_storage.recipe_id_seq', 3, true);


--
-- Name: recipe_request_id_seq; Type: SEQUENCE SET; Schema: pharmacy_storage; Owner: postgres
--

SELECT pg_catalog.setval('pharmacy_storage.recipe_request_id_seq', 3, true);


--
-- Name: user_account_id_seq; Type: SEQUENCE SET; Schema: pharmacy_storage; Owner: postgres
--

SELECT pg_catalog.setval('pharmacy_storage.user_account_id_seq', 7, true);


--
-- Name: user_data_id_seq; Type: SEQUENCE SET; Schema: pharmacy_storage; Owner: postgres
--

SELECT pg_catalog.setval('pharmacy_storage.user_data_id_seq', 2, true);


--
-- Name: user_order_id_seq; Type: SEQUENCE SET; Schema: pharmacy_storage; Owner: postgres
--

SELECT pg_catalog.setval('pharmacy_storage.user_order_id_seq', 7, true);


--
-- Name: category category_category_name_key; Type: CONSTRAINT; Schema: pharmacy_storage; Owner: postgres
--

ALTER TABLE ONLY pharmacy_storage.category
    ADD CONSTRAINT category_category_name_key UNIQUE (category_name);


--
-- Name: category category_pkey; Type: CONSTRAINT; Schema: pharmacy_storage; Owner: postgres
--

ALTER TABLE ONLY pharmacy_storage.category
    ADD CONSTRAINT category_pkey PRIMARY KEY (id);


--
-- Name: drug_dosage drug_dosage_pkey; Type: CONSTRAINT; Schema: pharmacy_storage; Owner: postgres
--

ALTER TABLE ONLY pharmacy_storage.drug_dosage
    ADD CONSTRAINT drug_dosage_pkey PRIMARY KEY (id);


--
-- Name: drug drug_name_key; Type: CONSTRAINT; Schema: pharmacy_storage; Owner: postgres
--

ALTER TABLE ONLY pharmacy_storage.drug
    ADD CONSTRAINT drug_name_key UNIQUE (name);


--
-- Name: drug drug_pkey; Type: CONSTRAINT; Schema: pharmacy_storage; Owner: postgres
--

ALTER TABLE ONLY pharmacy_storage.drug
    ADD CONSTRAINT drug_pkey PRIMARY KEY (id);


--
-- Name: payment_data payment_data_pkey; Type: CONSTRAINT; Schema: pharmacy_storage; Owner: postgres
--

ALTER TABLE ONLY pharmacy_storage.payment_data
    ADD CONSTRAINT payment_data_pkey PRIMARY KEY (id);


--
-- Name: recipe recipe_pkey; Type: CONSTRAINT; Schema: pharmacy_storage; Owner: postgres
--

ALTER TABLE ONLY pharmacy_storage.recipe
    ADD CONSTRAINT recipe_pkey PRIMARY KEY (id);


--
-- Name: recipe_request recipe_request_pkey; Type: CONSTRAINT; Schema: pharmacy_storage; Owner: postgres
--

ALTER TABLE ONLY pharmacy_storage.recipe_request
    ADD CONSTRAINT recipe_request_pkey PRIMARY KEY (id);


--
-- Name: user_account user_account_login_key; Type: CONSTRAINT; Schema: pharmacy_storage; Owner: postgres
--

ALTER TABLE ONLY pharmacy_storage.user_account
    ADD CONSTRAINT user_account_login_key UNIQUE (login);


--
-- Name: user_account user_account_pkey; Type: CONSTRAINT; Schema: pharmacy_storage; Owner: postgres
--

ALTER TABLE ONLY pharmacy_storage.user_account
    ADD CONSTRAINT user_account_pkey PRIMARY KEY (id);


--
-- Name: user_data user_data_pkey; Type: CONSTRAINT; Schema: pharmacy_storage; Owner: postgres
--

ALTER TABLE ONLY pharmacy_storage.user_data
    ADD CONSTRAINT user_data_pkey PRIMARY KEY (id);


--
-- Name: user_order user_order_pkey; Type: CONSTRAINT; Schema: pharmacy_storage; Owner: postgres
--

ALTER TABLE ONLY pharmacy_storage.user_order
    ADD CONSTRAINT user_order_pkey PRIMARY KEY (id);


--
-- Name: drug drug_category_id_fkey; Type: FK CONSTRAINT; Schema: pharmacy_storage; Owner: postgres
--

ALTER TABLE ONLY pharmacy_storage.drug
    ADD CONSTRAINT drug_category_id_fkey FOREIGN KEY (category_id) REFERENCES pharmacy_storage.category(id);


--
-- Name: drug_dosage drug_dosage_drug_id_fkey; Type: FK CONSTRAINT; Schema: pharmacy_storage; Owner: postgres
--

ALTER TABLE ONLY pharmacy_storage.drug_dosage
    ADD CONSTRAINT drug_dosage_drug_id_fkey FOREIGN KEY (drug_id) REFERENCES pharmacy_storage.drug(id);


--
-- Name: payment_data payment_data_user_id_fkey; Type: FK CONSTRAINT; Schema: pharmacy_storage; Owner: postgres
--

ALTER TABLE ONLY pharmacy_storage.payment_data
    ADD CONSTRAINT payment_data_user_id_fkey FOREIGN KEY (user_id) REFERENCES pharmacy_storage.user_account(id);


--
-- Name: recipe recipe_dosage_id_fkey; Type: FK CONSTRAINT; Schema: pharmacy_storage; Owner: postgres
--

ALTER TABLE ONLY pharmacy_storage.recipe
    ADD CONSTRAINT recipe_dosage_id_fkey FOREIGN KEY (dosage_id) REFERENCES pharmacy_storage.drug_dosage(id);


--
-- Name: recipe_request recipe_request_drug_dosage_id_fkey; Type: FK CONSTRAINT; Schema: pharmacy_storage; Owner: postgres
--

ALTER TABLE ONLY pharmacy_storage.recipe_request
    ADD CONSTRAINT recipe_request_drug_dosage_id_fkey FOREIGN KEY (drug_dosage_id) REFERENCES pharmacy_storage.drug_dosage(id);


--
-- Name: recipe_request recipe_request_user_id_fkey; Type: FK CONSTRAINT; Schema: pharmacy_storage; Owner: postgres
--

ALTER TABLE ONLY pharmacy_storage.recipe_request
    ADD CONSTRAINT recipe_request_user_id_fkey FOREIGN KEY (user_id) REFERENCES pharmacy_storage.user_account(id);


--
-- Name: recipe recipe_user_id_fkey; Type: FK CONSTRAINT; Schema: pharmacy_storage; Owner: postgres
--

ALTER TABLE ONLY pharmacy_storage.recipe
    ADD CONSTRAINT recipe_user_id_fkey FOREIGN KEY (user_id) REFERENCES pharmacy_storage.user_account(id);


--
-- Name: user_data user_data_user_id_fkey; Type: FK CONSTRAINT; Schema: pharmacy_storage; Owner: postgres
--

ALTER TABLE ONLY pharmacy_storage.user_data
    ADD CONSTRAINT user_data_user_id_fkey FOREIGN KEY (user_id) REFERENCES pharmacy_storage.user_account(id);


--
-- Name: user_order user_order_dosage_id_fkey; Type: FK CONSTRAINT; Schema: pharmacy_storage; Owner: postgres
--

ALTER TABLE ONLY pharmacy_storage.user_order
    ADD CONSTRAINT user_order_dosage_id_fkey FOREIGN KEY (dosage_id) REFERENCES pharmacy_storage.drug_dosage(id);


--
-- Name: user_order user_order_user_id_fkey; Type: FK CONSTRAINT; Schema: pharmacy_storage; Owner: postgres
--

ALTER TABLE ONLY pharmacy_storage.user_order
    ADD CONSTRAINT user_order_user_id_fkey FOREIGN KEY (user_id) REFERENCES pharmacy_storage.user_account(id);


--
-- PostgreSQL database dump complete
--

