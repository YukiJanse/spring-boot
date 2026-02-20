# Project Overview

This project began as a typical Spring Boot CRUD-style REST API built using a conventional layered architecture and JPA entities.

The application conceptually models the backend of a Twitter-like social media platform, including user management, profiles, and interaction-oriented features.

The repository documents an incremental refactoring effort toward Domain-Driven Design (DDD), focusing primarily on restructuring the User-related functionality while intentionally preserving the rest of the system in its original CRUD-oriented design.

The purpose of this project is to demonstrate practical understanding of both architectural styles and the transition between them.

---

# Motivation

Real-world software systems rarely start with ideal architecture.

Developers frequently work with existing CRUD-heavy or legacy codebases that require:

- Careful analysis
- Incremental refactoring
- Architectural evolution
- Preservation of existing behavior

Rather than rewriting the application, this project simulates a realistic refactoring scenario by progressively evolving part of the system toward a domain-driven design.

---

# Original Design (Before Refactoring)

The initial implementation followed a common Spring Boot structure:

- JPA entities functioning as primary business models
- Service layer containing most business logic
- Extensive use of primitive types (Long, String, etc.)
- Limited separation between domain rules and persistence concerns

This reflects how many production systems are initially designed.

---

# Refactoring Objective

The refactoring effort focuses on pragmatic architectural evolution:

- Introducing Domain-Driven Design concepts incrementally
- Separating domain logic from persistence models
- Improving model expressiveness and safety
- Avoiding disruptive rewrites
- Maintaining compatibility with existing infrastructure

Only the User-related functionality is migrated to a domain-centric design to mirror realistic partial-refactoring constraints.

---

# Key Refactoring Changes

## Introduction of Explicit Domain Model

User-related business logic was moved into a dedicated domain model.

Key characteristics:

- Aggregate Root (`User`)
- Intention-revealing behaviors (`changeEmail`, `changePassword`, etc.)
- Factory methods (`create`, `reconstruct`)
- Encapsulation of invariants
- Reduced dependency on framework-specific concerns

---

## Value Objects Replacing Primitive Types

Core business concepts are modeled as Value Objects rather than raw primitives.

Examples:

- `UserId`
- `Email`
- `Username`
- `HashedPassword`
- `DisplayName`

This improves type safety, readability, and prevents invalid states.

---

## Domain vs Persistence Separation

JPA entities are treated strictly as persistence representations.

- No business logic inside JPA entities
- Mapping handled by dedicated mapper classes
- Domain model independent from JPA / Hibernate

---

## Partial Migration Strategy

The remainder of the system intentionally retains its CRUD-oriented design.

This reflects realistic constraints where:

- Full rewrites are rarely feasible
- Architectural styles often coexist
- Systems evolve incrementally

---

# Architectural Insight Demonstrated

This project explores practical trade-offs between:

- CRUD-centric design
- Domain-driven design

and demonstrates how incremental refactoring can safely transition between them without destabilizing the system.

---

# Practical Skills Emphasized

This repository highlights skills relevant to working with existing production systems:

- Reading and analyzing legacy structures
- Identifying misplaced domain logic
- Incremental architectural refactoring
- Preserving behavioral compatibility
- Improving model safety and clarity
- Avoiding unnecessary rewrites

---

# Technologies Used

- Java
- Spring Boot
- Spring Security
- JPA / Hibernate
- Lombok
- MapStruct

---

# Intended Learning Outcome

This is not a pure Domain-Driven Design showcase.

Instead, the project demonstrates how a conventional CRUD application can be progressively refactored toward a more expressive and maintainable domain model.

---

# Author Notes

This repository reflects my interest in working with real-world systems where legacy code analysis, refactoring, and architectural improvement are critical.

The design choices prioritize pragmatic evolution and maintainability over theoretical purity.
