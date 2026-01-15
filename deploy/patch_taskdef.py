import json
import os

IMAGE_URI = os.environ["IMAGE_URI"]
TASK_ROLE_ARN = os.environ["TASK_ROLE_ARN"]
EX_ROLE_ARN = os.environ["EX_ROLE_ARN"]
ADOT_COL_IMAGE_URI = os.environ["ADOT_COL_IMAGE_URI"]
AMP_REMOTE_WRITE_ENDPOINT = os.environ["AMP_REMOTE_WRITE_ENDPOINT"]
APP_CONTAINER_NAME = os.environ["APP_CONTAINER_NAME"]
ADOT_COL_CONTAINER_NAME = os.environ["ADOT_COL_CONTAINER_NAME"]
GIT_SHA_BUILD = os.environ["GIT_SHA_BUILD"]


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

#name the application
containerDefinitions = td.get("containerDefinitions", [])

# Patch containers + remove unsupported fields
for c in containerDefinitions:
    if c.get("name") == "__APP_CONTAINER_NAME__":
        c["name"] = APP_CONTAINER_NAME
        c["image"] = IMAGE_URI

    elif c.get("name") == "__ADOT_COL_CONTAINER_NAME__":
        c["name"] = ADOT_COL_CONTAINER_NAME
        c["image"] = ADOT_COL_IMAGE_URI

        for env_var in c.get("environment", []):
            n = env_var.get("name")
            if n == "AMP_REMOTE_WRITE_ENDPOINT":
                env_var["value"] = AMP_REMOTE_WRITE_ENDPOINT
            elif n == "APP_CONTAINER_NAME":
                env_var["value"] = APP_CONTAINER_NAME
            elif n == "GIT_SHA_BUILD":
                env_var["value"] = GIT_SHA_BUILD

    # awsvpc does not support links, even as []
    c.pop("links", None)

with open("taskdef.json", "w") as f:
    json.dump(td, f, indent=2)

print("patched images:\n" + IMAGE_URI + ",\n" + ADOT_COL_IMAGE_URI)
