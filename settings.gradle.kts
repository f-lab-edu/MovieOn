rootProject.name = "MovieOn"

include("app")
include("common")

include(
    "product-api",
    "product-api:product-presentation",
    "product-api:product-application",
    "product-api:product-domain",
    "product-api:product-infrastructure",
)

include(
    "payment-api",
    "payment-api:payment-presentation",
    "payment-api:payment-application",
    "payment-api:payment-domain",
    "payment-api:payment-infrastructure",
)

include(
    "account-api",
    "account-api:account-presentation",
    "account-api:account-application",
    "account-api:account-domain",
    "account-api:account-infrastructure",
)

include(
    "notification-api",
    "notification-api:notification-presentation",
    "notification-api:notification-application",
    "notification-api:notification-domain",
    "notification-api:notification-infrastructure",
)

include(
    "order-api",
    "order-api:order-presentation",
    "order-api:order-application",
    "order-api:order-domain",
    "order-api:order-infrastructure",
)

include("query-api")
