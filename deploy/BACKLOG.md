- Make the rest of (Env, Region, etc) task.prod.json dynamic
- We can pass all of these values in from SSM and probably have on taskdef.json, then
  taskdef config source of truth becomes SSM. buildspec passes it in and python patches as needed.