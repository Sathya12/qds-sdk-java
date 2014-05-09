package com.qubole.qds.sdk.java.details;

import com.qubole.qds.sdk.java.api.InvokableBuilder;
import com.qubole.qds.sdk.java.api.PageableInvokableBuilder;
import com.qubole.qds.sdk.java.api.SchedulerApi;
import com.qubole.qds.sdk.java.client.QdsClient;
import com.qubole.qds.sdk.java.entities.SchedulesResponse;
import com.qubole.qds.sdk.java.entities.Status;
import com.qubole.qds.sdk.java.entities.SuccessAndStatus;

class SchedulerApiImpl implements SchedulerApi
{
    private final QdsClient client;

    @Override
    public PageableInvokableBuilder<SchedulesResponse> list()
    {
        return new GenericPageableInvokableBuilderImpl<SchedulesResponse>(client, null, SchedulesResponse.class, "scheduler");
    }

    @Override
    public InvokableBuilder<SuccessAndStatus> suspend(int scheduleId)
    {
        return suspendKillResume(scheduleId, "suspend");
    }

    @Override
    public InvokableBuilder<SuccessAndStatus> kill(int scheduleId)
    {
        return suspendKillResume(scheduleId, "kill");
    }

    @Override
    public InvokableBuilder<SuccessAndStatus> resume(int scheduleId)
    {
        return suspendKillResume(scheduleId, "resume");
    }

    SchedulerApiImpl(QdsClient client)
    {
        this.client = client;
    }

    private InvokableBuilder<SuccessAndStatus> suspendKillResume(int scheduleId, String status)
    {
        ClientEntity entity = new ClientEntity(new Status(status), ClientEntity.Method.PUT);
        return new GenericInvokableBuilderImpl<SuccessAndStatus>(client, entity, SuccessAndStatus.class, "scheduler", Integer.toString(scheduleId));
    }
}
