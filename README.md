# baby-blockchain
Sample implementation of a blockchain.

## Overview and purpose of the system / product
The purpose of this project is to create a simple blockchain implementation. 
The blockchain is a chain of blocks, where each block contains a hash of the previous block, a timestamp and transaction data. 
For this project, the blockchain will be used to store information about merchant reward program related transactions. 
The reward program shall have of issuance and redemption of loyalty points that will be stored in the blockchain.

## System content (system boundaries)
The system will consist of:
- a blockchain implementation, 
- an API layer to interact with the blockchain,
- The blockchain will be used to store the ledger of issuance and redemption of loyalty points.
- Sum of all issuance less redemption will be the current balance of loyalty points.

The following provide more clarity to the scope of the project:
- Storage of current balances of loyalty points will not be part of the blockchain. It will be stored in a separate database.
- Merchants interacting with the blockchain is expected to handle the reward calculation. The blockchain shall only record the ledger.

## Interaction (potential) of the product (with other products and components)
### Merchant Platform & Blockchain
The merchant platform will interact with the blockchain to record the issuance and redemption of loyalty points via API
- API to record issuance
- API to record redemption
- API to query ledger history from blockchain

### Consumer Interface & Blockchain
- API to initiate redemption at merchant's location
- API to query current balance of loyalty points
- API to query ledger history from blockchain

## Product features (short description)
- Immutable ledger of issuance and redemption of loyalty points
- traceability of loyalty points
- real-time balance update of loyalty points
- Secure: only authorized merchants can process issuance and redemption of loyalty points

## Security requirements
- Minimum key size is 512 bits
- Data integrity of the transactions in the ledger should be ensured by the blockchain
- Unauthorized changes to the ledger should not be possible

## Characteristics of users (who is the end user of the system)
- Privacy conscious consumers who are interested in earning and redeeming loyalty points
- Tech-savvy consumers who are comfortable with use of blockchain
- Early adopters of technology who are willing to try out innovative solutions
- Incentive driven consumers who also value transparency and security of the blockchain

## Restrictions
- The blockchain will not be distributed. It will be a single node implementation.
- Functionality takes precedence over performance
- Merchants would not be able to easily profile their customers on the blockchain
