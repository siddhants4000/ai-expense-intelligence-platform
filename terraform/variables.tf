variable "project_id" {
  description = "GCP project ID"
  type        = string
}

variable "region" {
  description = "GCP region"
  type        = string
  default     = "europe-west3"
}

variable "artifact_registry_repository" {
  description = "Artifact Registry Docker repository name"
  type        = string
  default     = "ai-expense-platform"
}