/**
 * Created by will on 2016/10/17.
 */

import Workspace from '../Workspace.js';
const w = new Workspace();


beforeAll(() => {
    global.Promise = require.requireActual('promise');
});

it('works with async/await', async () => {
    console.log('start test function:');
    const workspace = await w.createObj();
    expect(workspace.workspaceId).toBeInstanceOf(String);
    console.log('finished test function:');
});


// it('foo', async () => {
//     await Promise.resolve();
//     expect(1).toEqual(1);
// });

// import 'react-native';
//
// beforeAll(() => {
//     global.Promise = require.requireActual('promise');
// });
//
// it('foo', async () => {
//     await Promise.resolve();
//     expect(1).toEqual(1);
// });