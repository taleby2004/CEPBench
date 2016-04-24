# CEPBench
In this project we are about to generate tools for benching complex event processing systems.
There if a configuration manager which these attributes can be set:
1. Number of event types.
2. Number of attributes for each event type (from 10 to 100).
3. The distribution function for attributes in order to create random numbers.
3. Number of files that the event generator will send events to them. 
4. The method for dispatching events between files (Round robin , random , etc. ..)
5. Sending rate for each event type and distribution function.
6. Number of rules and their configs.
7. The method of serializing events to be saved in file. (protobuf, java serializer , etc...)

For these configs, one config file is generated and then the event generator will get the config file to create events and putting them in files (each  file is for one machine). it is possible for the users to ask the CEPBench to create final complex events. and then the created file can be compared with  the detected complex event in order to calculate miss rate, detection of false positive, false negative, etc

your cooperation is welcomed here ...
mehdi_talebi@comp.iust.ac.ir
mehdi_talebi@aut.ac.ir



