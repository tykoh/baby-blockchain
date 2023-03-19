package blockchain.baby.node;

import java.security.Security;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import blockchain.baby.node.jpa.BcBlock;
import blockchain.baby.node.jpa.RewardType;
import blockchain.baby.node.repo.BlockRepository;
import blockchain.baby.node.repo.RewardTypeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "blockchain.baby.node")
@EnableJpaRepositories(basePackages = "blockchain.baby.node.repo")
@EntityScan(basePackages = "blockchain.baby.node.jpa")
@EnableScheduling
public class NodeApplication {

	public static void main(String[] args) {
		Security.addProvider(new BouncyCastleProvider());
		SpringApplication.run(NodeApplication.class, args);
	}

	/*@Bean
	public CommandLineRunner demo(BlockRepository repository,
								  RewardTypeRepository rewardTypeRepository) {
		return (args) -> {

			BcBlock genesisBlock = new BcBlock();
			repository.save(genesisBlock);

			// fetch all blocks
			System.out.println("Blocks found with findAll():");
			System.out.println("-------------------------------");
			for (BcBlock block : repository.findAll()) {
				System.out.println(block);
			}

			RewardType rewardType = new RewardType();
			rewardType.setCode("ABCD");
			rewardType.setDescription("This is a test reward type");
			rewardType.setFingerprint("1234567890");
			rewardType.setName("Test Reward Type");
			rewardTypeRepository.save(rewardType);

			// fetch all reward type
			System.out.println("Reward Types found with findAll():");
			System.out.println("-------------------------------");
			for (RewardType reward : rewardTypeRepository.findAll()) {
				System.out.println(reward);
			}

		};
	}*/

}
