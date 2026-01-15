import json
import os

IMAGE_URI = os.environ["IMAGE_URI"]
TASK_ROLE_ARN = os.environ["TASK_ROLE_ARN"]
EX_ROLE_ARN = os.environ["EX_ROLE_ARN"]
ADOT_COL_IMAGE_URI = os.environ["ADOT_COL_IMAGE_URI"]
AMP_REMOTE_WRITE_ENDPOINT = os.environ["AMP_REMOTE_WRITE_ENDPOINT"]

with open("taskdef.json", "r") as f:
    td = json.load(f)

# Remove task-level readonly / describe-only fields. Note that readonly fields should already removed- this is a guard
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

# outer level
td["taskRoleArn"] = TASK_ROLE_ARN
td["executionRoleArn"] = EX_ROLE_ARN

# Patch containers + remove unsupported fields
for c in td.get("containerDefinitions", []):
    if c.get("name") == "app":
        c["image"] = IMAGE_URI

    elif c.get("name") == "adot-collector":
        c["image"] = ADOT_COL_IMAGE_URI

        for env_var in c.get("environment", []):
            if env_var.get("name") == "AMP_REMOTE_WRITE_ENDPOINT":
                env_var["value"] = AMP_REMOTE_WRITE_ENDPOINT

    # awsvpc does not support links, even as []
    c.pop("links", None)

with open("taskdef.json", "w") as f:
    json.dump(td, f, indent=2)

print("patched images:\n" + IMAGE_URI + ",\n" + ADOT_COL_IMAGE_URI)
