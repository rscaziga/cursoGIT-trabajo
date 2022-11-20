import React from 'react';
import {
    NumberField,
    Show,
    SimpleShowLayout,
    TextField,
} from 'react-admin';

const SourceTypeShow = props => (
    <Show {...props}>
        <SimpleShowLayout>
            <NumberField source="id" />
            <TextField source="description" />
        </SimpleShowLayout>
    </Show>
);

export default SourceTypeShow;
