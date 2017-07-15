package org.cloudbus.cloudsim;

import java.awt.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.core.CloudSimTags;
import org.cloudbus.cloudsim.lists.VmList;


/*class PesComparator implements Comparator<Pes>
	Cloudlet cl1, cl2;
	@Override
	public int compare(Pes cl1, Pes cl2) {
		// TODO Auto-generated method stub
		return cl1.getNumberOfPes().compareTo(cl2.getNumberOfPes);
	}
	
}*/

public class FirstMethodDatacenterBroker extends DatacenterBroker {
	
	public FirstMethodDatacenterBroker(String name) throws Exception {
		super(name);
		
	}

	@Override
	public void submitCloudlets(){
		Log.printLine("__________________________");
		ArrayList<Cloudlet> successfullySubmitted = new ArrayList<Cloudlet>();
		Log.printLine(cloudletsSubmitted);
		
		for (Cloudlet cloudlet : getCloudletList()) {
			Vm vm = null;
			if (cloudlet.getVmId() == -1) {
				// We must find an appropriate vm in which to place the cloudlet.
				Log.printLine("Number of total vms: " + getVmsCreatedList().size());
				for (Vm potential_vm : getVmsCreatedList()) {
					Log.printLine("Number of vm pe:" + " " + potential_vm.getNumberOfPes() + "     " + "Number of cloudlet pe:" + " " + cloudlet.getNumberOfPes());
					if (potential_vm.getNumberOfPes() >= cloudlet.getNumberOfPes()) {
						vm = potential_vm;
					}
				}

				if (vm == null) {
					// if we didn't find any good vm, we assign random.
			        Random rand = new Random();
				    int vmIndex = rand.nextInt(getVmsCreatedList().size());
	                vm = VmList.getById(getVmsCreatedList(), vmIndex);
				}
			} else {
				vm = VmList.getById(getVmsCreatedList(), cloudlet.getVmId());
			}
			
			Log.printLine(CloudSim.clock() + ": " + getName() + ": Sending cloudlet "+ cloudlet.getCloudletId() + " to VM#" + vm.getId());  
			cloudlet.setVmId(vm.getId());
			sendNow(getVmsToDatacentersMap().get(vm.getId()), CloudSimTags.CLOUDLET_SUBMIT, cloudlet);
			cloudletsSubmitted++;
			getCloudletSubmittedList().add(cloudlet);
			successfullySubmitted.add(cloudlet);
		}
	}

}
