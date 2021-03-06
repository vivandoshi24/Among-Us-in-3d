package de.vd24.amongus.model;

public enum TaskType {
    // General
    AcceptPower,
    DataTransfer,
    FixWiring,
    ClearGarbage,

    // Electrical
    DivertPower,

    // Medbay
    Scan,
    InspectSamples,

    // Weapons
    Shoot,

    // Reactor
    UnlockManifold,
    StartReactor,

    // Navigation
    StabilizeSteering,
    ChartCourse,

    // Admin
    SwipeCard,

    // Engines
    RefuelEngine,
    EngineMgmt,

    // Shields
    PrimeShields;
}
