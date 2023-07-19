rootProject.name = "RockPaperScissors"

include(
    "client",
    "common",
    "common:kryo",
    "server",
    "server:core",
    "server:mysql",
    "server:kryonet"
)