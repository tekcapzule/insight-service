package com.tekcapsule.insight.application.mapper;

import com.tekcapsule.core.domain.Command;
import com.tekcapsule.core.domain.ExecBy;
import com.tekcapsule.core.domain.Origin;
import com.tekcapsule.insight.application.function.input.CreateIndexRecordInput;
import com.tekcapsule.insight.application.function.input.CreateNewsInput;
import com.tekcapsule.insight.application.function.input.UpdateIndexRecordInput;
import com.tekcapsule.insight.application.function.input.UpdateNewsInput;
import com.tekcapsule.insight.domain.command.CreateIndexRecordCommand;
import com.tekcapsule.insight.domain.command.CreateNewsCommand;
import com.tekcapsule.insight.domain.command.UpdateIndexRecordCommand;
import com.tekcapsule.insight.domain.command.UpdateNewsCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.function.BiFunction;

@Slf4j
public final class InputOutputMapper {
    private InputOutputMapper() {

    }

    public static final BiFunction<Command, Origin, Command> addOrigin = (command, origin) -> {
        OffsetDateTime utc = OffsetDateTime.now(ZoneOffset.UTC);
        command.setChannel(origin.getChannel());
        command.setExecBy(ExecBy.builder().tenantId(origin.getTenantId()).userId(origin.getUserId()).build());
        command.setExecOn(utc.toString());
        return command;
    };

    public static final BiFunction<CreateIndexRecordInput, Origin, CreateIndexRecordCommand> buildCreateIndexRecordCommandFromCreateIndexRecordInput = (createInput, origin) -> {
        CreateIndexRecordCommand createIndexRecordCommand =  CreateIndexRecordCommand.builder().build();
        BeanUtils.copyProperties(createInput, createIndexRecordCommand);
        addOrigin.apply(createIndexRecordCommand, origin);
        return createIndexRecordCommand;
    };

    public static final BiFunction<UpdateIndexRecordInput, Origin, UpdateIndexRecordCommand> buildUpdateCreateIndexRecordCommandFromUpdateCreateIndexRecordInput = (updateInput, origin) -> {
        UpdateIndexRecordCommand updateIndexRecordCommand = UpdateIndexRecordCommand.builder().build();
        BeanUtils.copyProperties(updateInput, updateIndexRecordCommand);
        addOrigin.apply(updateIndexRecordCommand, origin);
        return updateIndexRecordCommand;
    };

    public static final BiFunction<CreateNewsInput, Origin, CreateNewsCommand> buildCreateNewsCommandFromCreateNewsInput = (createInput, origin) -> {
        CreateNewsCommand createNewsCommand =  CreateNewsCommand.builder().build();
        BeanUtils.copyProperties(createInput, createNewsCommand);
        addOrigin.apply(createNewsCommand, origin);
        return createNewsCommand;
    };

    public static final BiFunction<UpdateNewsInput, Origin, UpdateNewsCommand> buildUpdateNewsCommandFromUpdateNewsInput = (updateInput, origin) -> {
        UpdateNewsCommand updateNewsCommand = UpdateNewsCommand.builder().build();
        BeanUtils.copyProperties(updateInput, updateNewsCommand);
        addOrigin.apply(updateNewsCommand, origin);
        return updateNewsCommand;
    };


}
