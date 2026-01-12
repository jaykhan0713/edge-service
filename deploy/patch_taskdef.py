import json
import os

IMAGE_URI = os.environ["IMAGE_URI"]

with open("taskdef.json", "r") as f:
    td = json.load(f)

# Remove task-level readonly / describe-only fields
readonly = [
    "taskDefinitionArn",
    "revision",
    "status",
    "requiresAttributes",
    "compatibilities",
    "registeredAt",
    "registeredBy",
    "deregisteredAt",
]
for k in readonly:
    td.pop(k, None)

# Patch containers + remove unsupported fields
for c in td.get("containerDefinitions", []):
    if c.get("name") == "app":
        c["image"] = IMAGE_URI

    # awsvpc does not support links, even as []
    c.pop("links", None)

with open("taskdef.json", "w") as f:
    json.dump(td, f, indent=2)

print("patched image:", IMAGE_URI)
