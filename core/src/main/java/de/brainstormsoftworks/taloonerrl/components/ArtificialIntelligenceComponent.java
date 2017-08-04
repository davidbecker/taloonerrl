package de.brainstormsoftworks.taloonerrl.components;

import com.artemis.Component;
import com.artemis.annotations.PooledWeaver;

import de.brainstormsoftworks.taloonerrl.ai.BasicIntelligence;
import lombok.Getter;
import lombok.Setter;

/**
 * holder component for an artificial intelligence for an entity
 *
 * @author David Becker
 *
 */
@PooledWeaver
@Getter
@Setter
public class ArtificialIntelligenceComponent extends Component {

	BasicIntelligence artificialIntelligence = null;

	public void update() {
		if (artificialIntelligence != null) {
			artificialIntelligence.update();
		}
	}

}
