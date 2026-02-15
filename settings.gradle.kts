rootProject.name = "edge-service"

// Default: local dev uses the included project
// CI sets DISABLE_LOCAL_DTO=true to force resolving from CodeArtifact.
val disableLocalDto = System.getenv("DISABLE_LOCAL_DTO")
    ?.equals("true", ignoreCase = true) == true

if (!disableLocalDto) {
    include("openapi-dtos")
}