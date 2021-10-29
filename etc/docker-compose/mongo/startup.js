db.getSiblingDB("credits").createCollection("Contract")
db.getSiblingDB("credits").getCollection("Contract").createIndex({customer: 1})