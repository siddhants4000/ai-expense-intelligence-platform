output "artifact_registry_repository_name" {
  value = google_artifact_registry_repository.docker_repo.name
}

output "artifact_registry_repository_location" {
  value = google_artifact_registry_repository.docker_repo.location
}