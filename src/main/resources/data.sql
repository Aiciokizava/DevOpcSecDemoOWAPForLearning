INSERT INTO tasks (title, description, status, created_at) VALUES
  ('Write report', 'Prepare SBOM + DC + DT comparison', 'TODO', CURRENT_TIMESTAMP()),
  ('Fix pipeline', 'Make GitLab CI run only on MR', 'IN_PROGRESS', CURRENT_TIMESTAMP()),
  ('Push artifacts', 'Store reports for 1 week', 'DONE', CURRENT_TIMESTAMP());
