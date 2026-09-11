# GovFlow Intelligence v0.1 — Founder Demo Architecture

**Clean-room rule:** This repository is designed around synthetic data and a generic canonical event model. It must not contain employer/client source code, schemas, credentials, documents, screenshots, or confidential workflow details.

## Product Positioning
GovFlow Intelligence is a Government Transaction Intelligence & Prediction Platform.

## v0.1 Modules
1. Control Tower — operational KPIs and workload overview.
2. Bottleneck Radar — stage/department congestion detection.
3. Predictive SLA Engine — synthetic risk scoring in v0.1; replaceable by ML later.
4. What-If Simulator — capacity/volume scenario simulation.
5. Executive Copilot — natural-language interface over governed operational metrics (planned next increment).

## Canonical Transaction Model
- case_id
- created_at
- department
- stage
- transaction_type
- priority
- sla_hours
- age_hours
- queue_size
- delay_risk_pct
- status

## Target Architecture
Customer Systems -> GovFlow Connect -> Canonical Events -> Operational Store
-> Process Graph -> Prediction Engine -> Simulation Engine -> Decision Layer -> Control Tower/Copilot

## Recommended implementation path
- Frontend: Next.js + TypeScript
- Core API: Spring Boot
- Database: PostgreSQL
- Cache: Redis
- Analytics/ML service: isolated Python service only where model tooling is needed
- Deployment: Docker; private cloud/on-premise capable
- Auth: OIDC/OAuth2 + RBAC
- Audit: append-only AI/decision audit events

## Clean-Room Boundary
Adapters map customer fields into the canonical model. GovFlow core never assumes a vendor-specific table name or schema.
