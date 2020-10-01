package br.com.zup.pact.consumer.service

import br.com.zup.pact.consumer.dto.BalanceDTO
import br.com.zup.pact.consumer.dto.ClientDetailsDTO
import br.com.zup.pact.consumer.exception.ClientNotFoundException
import br.com.zup.pact.consumer.integration.service.AccountIntegrationService
import br.com.zup.pact.consumer.repository.ClientRepository
import org.springframework.beans.factory.annotation.Autowired

class ClientServiceImpl (
        @Autowired val clientRepository: ClientRepository,
        @Autowired val accountIntegrationService: AccountIntegrationService
): ClientService {
    override fun getClientDetails(clientId: Int): ClientDetailsDTO? {
        return clientRepository.findByClientId(clientId)
    }

    override fun getAll(): List<ClientDetailsDTO>? {
        return clientRepository.getAll()
    }

    override fun getBalance(clientId: Int): BalanceDTO? {
        val accountId: Int = getAccountId(clientId) ?: throw ClientNotFoundException("Client with id: $clientId not found")
        return accountId.let { accountIntegrationService.getBalance(it) }
    }

    private fun getAccountId(clientId: Int): Int? {
        return getClientDetails(clientId)?.accountId
    }
}