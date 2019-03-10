class LiveNetworkGraph(appContext: Context) : BaseNetworkGraph(
    networkConfig = NetworkConfigs.S_3_LIVE_ENDPOINT,
    appContext = appContext
) {

    /**
     * Our S3 server can't support POST calls,
     * so we are just returning a mock for this call.
     */
    override val userRepo: UserRepo =
        object : UserRepo {
            override fun login(loginRequest: LoginRequest): Single<User> {
                val user = User(
                    firstname = "Live",
                    lastname = "User"
                )
                return Single.fromCallable { user }
            }
        }
}