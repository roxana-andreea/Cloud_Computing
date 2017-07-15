package org.cloudbus.cloudsim;

import java.util.Random;
import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.DatacenterBroker;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.core.CloudSimTags;
import org.cloudbus.cloudsim.lists.VmList;

public class RandomDatacenterBroker extends DatacenterBroker{

	public RandomDatacenterBroker(String name) throws Exception {
		super(name);
	}
	
	protected int randomSubmit(Cloudlet cloudlet, int vmIndex, Vm vm){
        Random rand = new Random();
        int randomValue = rand.nextInt(getVmsCreatedList().size());
        Log.printLine("Random Value= " +randomValue);
         
         
        Log.printLine(CloudSim.clock() + ": " + getName() + ": Sending cloudlet "+ cloudlet.getCloudletId() + " to VM#" + vm.getId());
        cloudlet.setVmId(vm.getId());
        sendNow(getVmsToDatacentersMap().get(vm.getId()), CloudSimTags.CLOUDLET_SUBMIT, cloudlet);
        cloudletsSubmitted++;
        //Log.printLine(vmIndex);
        vmIndex = (vmIndex + 1) % getVmsCreatedList().size();
        getCloudletSubmittedList().add(cloudlet);
        return randomValue;
    }
    
    @Override
    protected void submitCloudlets(){
        Log.printLine("__________________________");    
        Random rand = new Random();
        int randomValue = rand.nextInt(getVmsCreatedList().size());
        int vmIndex = randomValue;
        
       
        Log.printLine(cloudletsSubmitted);
        for (Cloudlet cloudlet : getCloudletList()) {
            Vm vm;
            // if user didn't bind this cloudlet and it has not been executed yet
            if (cloudlet.getVmId() == -1) {
                    vm = getVmsCreatedList().get(vmIndex);
                    //Log.printLine("not bind");
            } else { // submit to the specific vm
                vm = VmList.getById(getVmsCreatedList(), cloudlet.getVmId());
                if (vm == null) { // vm was not created
                           Log.printLine(CloudSim.clock() + ": " + getName() + ": Postponing execution of cloudlet "
                                          + cloudlet.getCloudletId() + ": bount VM not available");
                         continue;
                }
            }
           // vmIndex=randomSubmit(cloudlet,vmIndex,vm);
            Log.printLine(CloudSim.clock() + ": " + getName() + ": Sending cloudlet "+ cloudlet.getCloudletId() + " to VM#" + vm.getId());
            cloudlet.setVmId(vm.getId());
            sendNow(getVmsToDatacentersMap().get(vm.getId()), CloudSimTags.CLOUDLET_SUBMIT, cloudlet);
            cloudletsSubmitted++;
            //Log.printLine(vmIndex);
            
            vmIndex = rand.nextInt(getVmsCreatedList().size());;
            getCloudletSubmittedList().add(cloudlet);
        }
    }
	


	
}
