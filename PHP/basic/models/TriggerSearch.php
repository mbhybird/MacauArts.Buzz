<?php

namespace app\models;

use Yii;
use yii\base\Model;
use yii\data\ActiveDataProvider;
use app\models\Trigger;

/**
 * TriggerSearch represents the model behind the search form about `app\models\Trigger`.
 */
class TriggerSearch extends Trigger
{
    /**
     * @inheritdoc
     */
    public function rules()
    {
        return [
            [['id', 'triggertype', 'triggercount', 'triggerfrequency'], 'integer'],
        ];
    }

    /**
     * @inheritdoc
     */
    public function scenarios()
    {
        // bypass scenarios() implementation in the parent class
        return Model::scenarios();
    }

    /**
     * Creates data provider instance with search query applied
     *
     * @param array $params
     *
     * @return ActiveDataProvider
     */
    public function search($params)
    {
        $query = Trigger::find();

        $dataProvider = new ActiveDataProvider([
            'query' => $query,
        ]);

        $this->load($params);

        if (!$this->validate()) {
            // uncomment the following line if you do not want to any records when validation fails
            // $query->where('0=1');
            return $dataProvider;
        }

        $query->andFilterWhere([
            'id' => $this->id,
            'triggertype' => $this->triggertype,
            'triggercount' => $this->triggercount,
            'triggerfrequency' => $this->triggerfrequency,
        ]);

        return $dataProvider;
    }
}
