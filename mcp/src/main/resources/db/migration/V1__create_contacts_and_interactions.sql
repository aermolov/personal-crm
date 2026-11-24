CREATE TABLE contacts (
    id UUID PRIMARY KEY,
    owner_sub TEXT NOT NULL,
    name TEXT NOT NULL CHECK (btrim(name) <> ''),
    email TEXT,
    notes TEXT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now()
);

CREATE INDEX contacts_owner_sub_idx ON contacts (owner_sub);

CREATE TABLE interactions (
    id UUID PRIMARY KEY,
    owner_sub TEXT NOT NULL,
    contact_id UUID NOT NULL REFERENCES contacts (id),
    occurred_at TIMESTAMP WITH TIME ZONE NOT NULL,
    summary TEXT NOT NULL CHECK (btrim(summary) <> '')
);

CREATE INDEX interactions_owner_sub_idx ON interactions (owner_sub);
CREATE INDEX interactions_owner_sub_contact_id_idx ON interactions (owner_sub, contact_id);
