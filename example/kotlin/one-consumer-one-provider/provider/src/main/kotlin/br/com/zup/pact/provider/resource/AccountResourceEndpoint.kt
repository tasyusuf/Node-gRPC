package br.com.zup.pact.provider.resource

import br.com.zup.pact.provider.dto.AccountDetailsDTO
import br.com.zup.pact.provider.dto.BalanceDTO
import br.com.zup.pact.provider.service.AccountService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/v1/accounts")
class AccountResourceEndpoint(val accountService: AccountService) {
    @GetMapping("/{clientId}")
    fun getAccountDetailsByClientId(@PathVariable("clientId") clientId: Int): ResponseEntity<AccountDetailsDTO>{
        val accountDetailsDTO: Optional<AccountDetailsDTO> = accountService.getAccountDetailsByClientId(clientId)
        return if (accountDetailsDTO.isPresent) ResponseEntity(accountDetailsDTO.get(), HttpStatus.OK)
            else ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @GetMapping
    fun getAll(): ResponseEntity<List<AccountDetailsDTO>> {
        val allAccounts: List<AccountDetailsDTO> = accountService.getAll()
        return if (allAccounts.isNotEmpty()) ResponseEntity(allAccounts, HttpStatus.OK)
            else ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @GetMapping("/balance/{clientId}")
    fun getBalanceByClientId(@PathVariable("clientId") clientId: Int): ResponseEntity<BalanceDTO> {
        val balanceDTO: Optional<BalanceDTO> = accountService.getBalanceByClientId(clientId)
        return if (balanceDTO.isPresent) ResponseEntity(balanceDTO.get(), HttpStatus.OK)
            else ResponseEntity(HttpStatus.NOT_FOUND)
    }
}
