package ktor

import AuthRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.path
import ktor.models.KtorSearchGame
import ktor.models.KtorSearchRequest

class KtorGamesDataSource(
    private val httpClient: HttpClient,
    private val authRepository: AuthRepository,
) {
    suspend fun fetchAllGames(): List<KtorSearchGame> {
        val token = authRepository.getToken()
        return httpClient.post {
            header("Bearer-Authorization", token)

            url {
                path("games/search")
                setBody(KtorSearchRequest(searchQuery = ""))
            }
        }.body()
    }

    suspend fun searchGame(query: String): List<KtorSearchGame> {
        val token = authRepository.getToken()
        return httpClient.post {
            header("Bearer-Authorization", token)

            url {
                path("games/search")
                setBody(KtorSearchRequest(searchQuery = query))
            }
        }.body()
    }
}