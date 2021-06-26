package com.fitness.fitnesstracker.util

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*
import java.util.function.Function


@Component
class JwtTokenUtil {


    fun getUsernameFromToken(token: String): String? {
        return getClaimFromToken(token, Function { it.subject })
    }

    fun getExpirationDateFromToken(token: String): Date? {
        return getClaimFromToken(token, Function { it.expiration })
    }

    fun <T> getClaimFromToken(token: String, claimsResolver: Function<Claims, T>): T? {
        val claims = getAllClaimsFromToken(token)
        return claims?.let { claimsResolver.apply(it) }
    }

    private fun getAllClaimsFromToken(token: String): Claims? {
        return Jwts.parser().setSigningKey("filterapp").parseClaimsJws(token).body
    }

    private fun isTokenExpired(token: String): Boolean {
        val expiration = getExpirationDateFromToken(token)
        if (expiration != null) {
            val value= expiration.after(Date())
            return value
        }

        return false
    }


    fun validateToken(token: String, userDetails: UserDetails): Boolean {
        val username = getUsernameFromToken(token)
        System.out.println(username == userDetails.username)
        val checkpoint = (username == userDetails.username && isTokenExpired(token))
        return checkpoint
    }

    fun generateToken(userDetails: UserDetails): String {
        val claims: MutableMap<String, Any> = HashMap()
        val userRoles: MutableSet<String> = HashSet()
        userDetails.authorities.forEach { action: GrantedAuthority? -> userRoles.add(action!!.authority) }
        claims["Roles"] = userRoles.toTypedArray()
        return doGenerateToken(claims, userDetails.username)
    }

    fun doGenerateToken(claims: MutableMap<String, Any>, subject: String): String {

        return Jwts.builder().setClaims(claims).setSubject(subject)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(
                Date(
                    System.currentTimeMillis() + 24 * 100000
                )
            )
            .signWith(SignatureAlgorithm.HS512, "filterapp").compact()
    }


    companion object

}